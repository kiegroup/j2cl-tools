/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.kie.j2cl.tools.di.apt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.kie.j2cl.tools.di.annotation.Application;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.generator.BeanManagerProducerGenerator;
import org.kie.j2cl.tools.di.apt.generator.BootstrapperGenerator;
import org.kie.j2cl.tools.di.apt.generator.ManagedBeanGenerator;
import org.kie.j2cl.tools.di.apt.generator.ObservesGenerator;
import org.kie.j2cl.tools.di.apt.generator.ProducesGenerator;
import org.kie.j2cl.tools.di.apt.generator.ProxyGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.context.GenerationContext;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.PrintWriterTreeLogger;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.task.AfterBurnFactoryStepTask;
import org.kie.j2cl.tools.di.apt.task.BeanInfoGenerator;
import org.kie.j2cl.tools.di.apt.task.BeanProcessorTask;
import org.kie.j2cl.tools.di.apt.task.FactoryGeneratorTask;
import org.kie.j2cl.tools.di.apt.task.FireBeforeTask;
import org.kie.j2cl.tools.di.apt.task.IOCProviderTask;
import org.kie.j2cl.tools.di.apt.task.MethodParamDecoratorTask;
import org.kie.j2cl.tools.di.apt.task.ProcessGraphTask;
import org.kie.j2cl.tools.di.apt.task.ProcessSubClassesTask;
import org.kie.j2cl.tools.di.apt.task.TaskGroup;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ApplicationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Application.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }

        final TreeLogger logger = new PrintWriterTreeLogger();
        final long start = System.currentTimeMillis();

        Optional<TypeElement> maybeApplication = processApplicationAnnotation(roundEnvironment, logger);
        if (maybeApplication.isEmpty()) {
            return false;
        }

        logger.log(TreeLogger.INFO, "DI generation started ...");

        TypeElement application = maybeApplication.get();
        GenerationContext context = new GenerationContext(application, roundEnvironment, processingEnv,
                logger.branch(TreeLogger.DEBUG, "start classpath scan ..."));

        final long finished = (System.currentTimeMillis() - start);

        logger.log(TreeLogger.INFO, "classpath processed in " + finished / 1000 + "s");
        if (finished > 1000) {
            logger.log(TreeLogger.INFO,
                    "ClassPath scan is slow, reduce the number of jars in the classpath/dependencies.");
        }

        IOCContext iocContext = new IOCContext(context);
        ContextHolder.getInstance().setContext(iocContext);

        logger.log(TreeLogger.INFO,
                "IOCContext created in " + (System.currentTimeMillis() - start) + " ms");

        long startgen = System.currentTimeMillis();

        initAndRegisterGenerators(iocContext, logger.branch(TreeLogger.DEBUG, "start generators scan"));

        logger.log(TreeLogger.INFO,
                "Generators registered in  " + (System.currentTimeMillis() - startgen) + " ms");

        TaskGroup taskGroup = new TaskGroup(logger.branch(TreeLogger.DEBUG, "start processing"));
        taskGroup.addTask(new FireBeforeTask(iocContext, logger));
        taskGroup.addTask(new IOCProviderTask(iocContext, logger));
        taskGroup.addTask(new BeanProcessorTask(iocContext, logger));
        taskGroup.addTask(new ProcessSubClassesTask(iocContext, logger));
        taskGroup.addTask(new ProcessGraphTask(iocContext, logger, application));
        taskGroup.addTask(new MethodParamDecoratorTask(iocContext, logger));
        taskGroup.addTask(new FactoryGeneratorTask(iocContext, logger));
        taskGroup.addTask(new BeanInfoGenerator(iocContext, logger));
        taskGroup.addTask(new AfterBurnFactoryStepTask(iocContext, logger));
        taskGroup.execute();


        logger.log(TreeLogger.INFO,
                "DI generation finished in " + (System.currentTimeMillis() - start) + " ms");

        return true;
    }

    private Optional<TypeElement> processApplicationAnnotation(RoundEnvironment roundEnvironment,
                                                               TreeLogger logger) {
        Set<Element> applications =
                (Set<Element>) roundEnvironment.getElementsAnnotatedWith(Application.class);

        if (applications.isEmpty()) {
            logger.log(TreeLogger.ERROR, "No classes annotated with @Application detected");
            return Optional.empty();
        }

        if (applications.size() > 1) {
            logger.log(TreeLogger.ERROR, "There is must be only one class annotated with @Application\"");
            throw new GenerationException();
        }

        Optional<Element> candidate = applications.stream().findFirst();

        if (candidate.isPresent()) {
            if (!candidate.get().getKind().isClass()) {
                logger.log(TreeLogger.ERROR, "The class annotated with @Application must be a class\"");
                throw new GenerationException();
            }

            if (candidate.get().getModifiers().contains(javax.lang.model.element.Modifier.ABSTRACT)) {
                logger.log(TreeLogger.ERROR,
                        "The class annotated with @Application must not be abstract\"");
                throw new GenerationException();
            }
            return candidate.map(TypeElement.class::cast);
        }
        return Optional.empty();
    }

    private void initAndRegisterGenerators(IOCContext iocContext, TreeLogger logger) {
        Set<IOCGenerator<?>> buildIn = new HashSet<>();
        buildIn.add(new BeanManagerProducerGenerator(logger, iocContext));
        buildIn.add(new BootstrapperGenerator(logger, iocContext));
        buildIn.add(new ObservesGenerator(logger, iocContext));
        buildIn.add(new ProducesGenerator(logger, iocContext));
        buildIn.add(new ProxyGenerator(logger, iocContext));
        buildIn.add(new ManagedBeanGenerator(logger, iocContext));

        ScanResult scanResult = iocContext.getGenerationContext().getScanResult();

        ClassInfoList routeClassInfoList =
                scanResult.getClassesWithAnnotation(Generator.class.getCanonicalName());

        for (ClassInfo routeClassInfo : routeClassInfoList) {
            try {
                Constructor c = Class.forName(routeClassInfo.getName()).getConstructor(TreeLogger.class,
                        IOCContext.class);
                IOCGenerator<?> generator = ((IOCGenerator<?>) c.newInstance(
                        logger.branch(TreeLogger.INFO, "register generator: " + routeClassInfo.getName()),
                        iocContext));
                buildIn.add(generator);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                     | NoSuchMethodException | InvocationTargetException e) {
                throw new GenerationException(e);
            }
        }
        for (IOCGenerator<?> generator : buildIn) {
            registerGenerator(generator, logger);
        }
    }

    private void registerGenerator(IOCGenerator<?> generator, TreeLogger logger) {
        generator.register();
    }

}
