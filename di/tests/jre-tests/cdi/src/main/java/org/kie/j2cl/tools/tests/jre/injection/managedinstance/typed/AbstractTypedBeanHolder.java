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

package org.kie.j2cl.tools.tests.jre.injection.managedinstance.typed;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.ManagedInstance;

@ApplicationScoped
public class AbstractTypedBeanHolder {

  public final ManagedInstance<AbstractTyped> instance;

  @Inject
  AbstractTypedBeanHolder(@Any @Default ManagedInstance<AbstractTyped> instance) {
    this.instance = instance;
  }
}
