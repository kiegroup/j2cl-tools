/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.j2cl.tools.yaml.mapper.context;

import com.google.auto.common.MoreTypes;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.yaml.mapper.TypeRegistry;
import org.kie.j2cl.tools.yaml.mapper.TypeUtils;
import org.kie.j2cl.tools.yaml.mapper.definition.BeanDefinition;

public class GenerationContext {

  private final RoundEnvironment roundEnvironment;
  private final ProcessingEnvironment processingEnv;
  private final TypeRegistry typeRegistry;
  private final TypeUtils typeUtils;
  private final Map<TypeMirror, BeanDefinition> beans = new HashMap<>();

  public GenerationContext(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnv) {
    this.processingEnv = processingEnv;
    this.roundEnvironment = roundEnvironment;
    this.typeRegistry = new TypeRegistry(this);
    this.typeUtils = new TypeUtils(this);
  }

  public RoundEnvironment getRoundEnvironment() {
    return roundEnvironment;
  }

  public ProcessingEnvironment getProcessingEnv() {
    return processingEnv;
  }

  public TypeRegistry getTypeRegistry() {
    return typeRegistry;
  }

  public TypeUtils getTypeUtils() {
    return typeUtils;
  }

  public BeanDefinition getBeanDefinition(TypeMirror type) {
    if (beans.containsKey(type)) {
      return beans.get(type);
    } else {
      BeanDefinition beanDefinition = new BeanDefinition(MoreTypes.asTypeElement(type), this);
      beans.put(type, beanDefinition);
      return beanDefinition;
    }
  }

  public void addBeanDefinition(TypeElement type) {
    System.out.println("Adding bean definition for " + type.getQualifiedName());
    if (!beans.containsKey(type.asType())) {
      BeanDefinition beanDefinition = new BeanDefinition(type, this);
      beans.put(type.asType(), beanDefinition);
    }
  }

  public Collection<BeanDefinition> getBeans() {
    return beans.values();
  }
}