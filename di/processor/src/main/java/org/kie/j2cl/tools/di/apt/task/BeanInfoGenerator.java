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

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.FilerException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import com.google.auto.common.MoreTypes;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.ProducesBeanDefinition;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.ExecutionEnv;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.generator.helpers.FreemarkerTemplateGenerator;
import org.kie.j2cl.tools.di.apt.generator.info.AbstractBeanInfoGenerator;
import org.kie.j2cl.tools.di.apt.generator.info.BeanInfoJREGeneratorBuilder;
import org.kie.j2cl.tools.di.apt.generator.info.InterceptorGenerator;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;

import static javax.lang.model.element.Modifier.ABSTRACT;

public class BeanInfoGenerator implements Task {

  private IOCContext iocContext;
  private AbstractBeanInfoGenerator generator;

  private InterceptorGenerator interceptorGenerator;


  public BeanInfoGenerator(IOCContext iocContext, TreeLogger logger) {
    this.iocContext = iocContext;
    if (iocContext.getGenerationContext().getExecutionEnv().equals(ExecutionEnv.JRE)) {
      generator = new BeanInfoJREGeneratorBuilder(iocContext);
      interceptorGenerator = new InterceptorGenerator(iocContext);
    }
  }

  public void execute() throws UnableToCompleteException {
    if (generator == null) {
      return;
    }

    new InfoGenerator().generate();

    for (TypeMirror bean : iocContext.getOrderedBeans()) {
      TypeMirror erased = iocContext.getGenerationContext().getTypes().erasure(bean);
      BeanDefinition beanDefinition = iocContext.getBean(erased);
      if (beanDefinition instanceof ProducesBeanDefinition) {
        continue;
      }
      if (isSuitableBeanDefinition(beanDefinition)) {
        beanDefinition.getIocGenerator()
            .ifPresent(iocGenerator -> interceptorGenerator.generate(beanDefinition));
      }
    }
  }

  private boolean isSuitableBeanDefinition(BeanDefinition beanDefinition) {
    if (beanDefinition.getIocGenerator().isPresent()) {
      return true;
    }
    return MoreTypes.asTypeElement(beanDefinition.getType()).getKind().isClass()
        && !MoreTypes.asTypeElement(beanDefinition.getType()).getModifiers().contains(ABSTRACT);
  }

  private class InfoGenerator {

    private final FreemarkerTemplateGenerator freemarkerTemplateGenerator =
        new FreemarkerTemplateGenerator("jre/parent.ftlh");

    private void generate() {

      try {
        String source = freemarkerTemplateGenerator.toSource(new Object());
        String fileName = "org.kie.j2cl.tools.di.apt.generator.info.Info";
        write(iocContext, fileName, source);
      } catch (IOException e) {
        throw new GenerationException(e);
      }
    }

    private void write(IOCContext iocContext, String fileName, String source) throws IOException {

      try {
        JavaFileObject sourceFile = iocContext.getGenerationContext().getProcessingEnvironment()
            .getFiler().createSourceFile(fileName);
        try (Writer writer = sourceFile.openWriter()) {
          writer.write(source);
        }
      } catch (FilerException ignored) {
      }
    }
  }
}
