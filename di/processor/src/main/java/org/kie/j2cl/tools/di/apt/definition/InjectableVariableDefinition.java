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

import java.util.Optional;

import javax.lang.model.element.VariableElement;

import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;

public class InjectableVariableDefinition extends VariableDefinition implements Injectable {

  private Optional<IOCGenerator<BeanDefinition>> generator = Optional.empty();
  private Optional<BeanDefinition> implementation = Optional.empty();

  public InjectableVariableDefinition(BeanDefinition parent, VariableElement variableElement) {
    super(parent, variableElement);
  }

  @Override
  public Optional<IOCGenerator<BeanDefinition>> getGenerator() {
    return generator;
  }

  @Override
  public void setGenerator(IOCGenerator<BeanDefinition> generator) {
    this.generator = Optional.of(generator);
  }

  @Override
  public Optional<BeanDefinition> getImplementation() {
    return implementation;
  }

  @Override
  public void setImplementation(BeanDefinition implementation) {
    this.implementation = Optional.of(implementation);
  }
}
