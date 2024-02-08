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

package org.kie.j2cl.tools.tests.jre.injection.qualifiers.typed;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.inject.Inject;

@ApplicationScoped
@Typed(SessionCommandManager.class)
public class ApplicationCommandManager implements SessionCommandManager<AbstractCanvasHandler> {

  public RegistryAwareCommandManager commandManagerInstances;

  public ApplicationCommandManager() {

  }

  @Inject
  public ApplicationCommandManager(final RegistryAwareCommandManager commandManagerInstances) {
    this.commandManagerInstances = commandManagerInstances;
  }

}
