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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;

import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;

public class MethodDefinition implements Definition {

  private final ExecutableElement executableElement;
  private final BeanDefinition beanDefinition;
  private final Set<IOCGenerator<MethodDefinition>> decorators = new HashSet<>();


  MethodDefinition(BeanDefinition beanDefinition, ExecutableElement executableElement) {
    this.beanDefinition = beanDefinition;
    this.executableElement = executableElement;
  }

  public ExecutableElement getExecutableElement() {
    return executableElement;
  }

  public BeanDefinition getBeanDefinition() {
    return beanDefinition;
  }

  public Set<IOCGenerator<MethodDefinition>> getDecorators() {
    return decorators;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MethodDefinition that = (MethodDefinition) o;
    return executableElement.equals(that.executableElement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executableElement);
  }
}
