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

package org.kie.j2cl.tools.di.apt.task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.lang.model.element.TypeElement;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.validation.GeneratorValidator;

public class InitAndRegisterGeneratorsTask implements Task {

    private IOCContext context;
    private TreeLogger logger;

    private GeneratorValidator generatorValidator;

    public InitAndRegisterGeneratorsTask(IOCContext context, TreeLogger logger) {
        this.context = context;
        this.logger = logger;
        this.generatorValidator = new GeneratorValidator(context);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute() throws UnableToCompleteException {
        ScanResult scanResult = context.getGenerationContext().getScanResult();
        ClassInfoList routeClassInfoList =
                scanResult.getClassesWithAnnotation(Generator.class.getCanonicalName());
        for (ClassInfo routeClassInfo : routeClassInfoList) {
            try {
                String generator = routeClassInfo.getName();
                TypeElement typeElement =
                        context.getGenerationContext().getElements().getTypeElement(generator);
                generatorValidator.validate(typeElement);
                Constructor c = Class.forName(generator).getConstructor(TreeLogger.class, IOCContext.class);
                ((IOCGenerator<?>) c.newInstance(
                        logger.branch(TreeLogger.INFO, "register generator: " + routeClassInfo.getName()),
                        this.context)).register();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                     | NoSuchMethodException | InvocationTargetException e) {
                throw new GenerationException(e);
            }
        }
    }
}
