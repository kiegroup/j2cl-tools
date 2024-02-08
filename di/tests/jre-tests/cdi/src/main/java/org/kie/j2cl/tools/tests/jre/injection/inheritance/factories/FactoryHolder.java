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

package org.kie.j2cl.tools.tests.jre.injection.inheritance.factories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.ManagedInstance;

@ApplicationScoped
public class FactoryHolder {

  private final ManagedInstance<DefinitionFactory> definitionFactoryInstances;

  @Inject
  private ManagedInstance<DefinitionFactory> definitionFactoryInstances2;

  @Inject
  public FactoryHolder(final ManagedInstance<DefinitionFactory> definitionFactoryInstances) {
    this.definitionFactoryInstances = definitionFactoryInstances;
  }


  public ManagedInstance<DefinitionFactory> getDefinitionFactoryInstances() {
    return definitionFactoryInstances;
  }
}
