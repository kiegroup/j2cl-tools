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

import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.MethodDefinition;
import org.kie.j2cl.tools.di.apt.definition.MethodDefinitionFactory;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;

public class MethodParamDecoratorTask implements Task {

  private IOCContext iocContext;
  private TreeLogger logger;
  private final MethodDefinitionFactory methodDefinitionFactory;


  public MethodParamDecoratorTask(IOCContext context, TreeLogger logger) {
    this.iocContext = context;
    this.logger = logger;

    this.methodDefinitionFactory = new MethodDefinitionFactory(iocContext, logger);
  }

  @Override
  public void execute() throws UnableToCompleteException {
    iocContext.getGenerators().asMap().entrySet().stream()
        .filter(iocGeneratorMetaCollectionEntry -> iocGeneratorMetaCollectionEntry
            .getKey().wiringElementType.equals(WiringElementType.PARAMETER))
        .forEach(iocGeneratorMetaCollectionEntry -> {
          iocGeneratorMetaCollectionEntry.getValue().forEach(gen -> {

            Set<ExecutableElement> elements = iocContext
                .getParametersByAnnotation(iocGeneratorMetaCollectionEntry.getKey().annotation)
                .stream().filter(elm -> elm.getKind().equals(ElementKind.PARAMETER))
                .map(MoreElements::asVariable)
                .map(elm -> MoreElements.asExecutable(elm.getEnclosingElement()))
                .map(MoreElements::asExecutable).collect(Collectors.toSet());

            elements.stream().forEach(e -> {
              TypeMirror erased = iocContext.getGenerationContext().getTypes()
                  .erasure(e.getEnclosingElement().asType());
              BeanDefinition bean = iocContext.getBeans().get(erased);
              ExecutableType methodType = (ExecutableType) e.asType();
              bean.getMethods().stream()
                  .filter(mmethod -> MoreTypes.asExecutable(mmethod.getExecutableElement().asType())
                      .equals(methodType))
                  .findFirst().orElse(methodDefinitionFactory.of(bean, e)).getDecorators()
                  .addAll(iocGeneratorMetaCollectionEntry.getValue().stream()
                      .map(em -> (IOCGenerator<MethodDefinition>) em).collect(Collectors.toSet()));
            });
          });
        });

    iocContext.getBeans().values().forEach(bean -> bean.getSubclasses().forEach(sub -> {
      bean.getMethods().forEach(subMethod -> {
        if (!sub.getMethods().contains(subMethod)) {
          sub.getMethods().add(subMethod);
        }
      });
    }));
  }
}
