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

package org.kie.j2cl.tools.di.apt.definition;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.processor.ConstructorInjectionPointProcessor;
import org.kie.j2cl.tools.di.apt.processor.FieldProcessor;
import org.kie.j2cl.tools.di.apt.util.TypeUtils;

public class BeanDefinitionFactory {

  private final IOCContext context;
  private final TreeLogger logger;
  private final FieldProcessor fieldProcessor;
  private final ConstructorInjectionPointProcessor constructorInjectionPointProcessor;

  public BeanDefinitionFactory(IOCContext context, TreeLogger logger) {
    this.context = context;
    this.logger = logger;
    this.fieldProcessor = new FieldProcessor(context, logger);
    this.constructorInjectionPointProcessor =
        new ConstructorInjectionPointProcessor(context, logger);
  }

  public BeanDefinition of(TypeMirror type) throws UnableToCompleteException {
    validateBean(type);
    BeanDefinition bean = new BeanDefinition(type);
    fieldProcessor.process(bean);
    constructorInjectionPointProcessor.process(bean);
    return bean;
  }

  public ProducesBeanDefinition of(ExecutableElement produces) throws UnableToCompleteException {
    List<AnnotationMirror> qualifiers =
        TypeUtils.getAllElementQualifierAnnotations(context, produces);
    return new ProducesBeanDefinition(produces, qualifiers);
  }

  private void validateBean(TypeMirror type) {
    // TODO
  }
}
