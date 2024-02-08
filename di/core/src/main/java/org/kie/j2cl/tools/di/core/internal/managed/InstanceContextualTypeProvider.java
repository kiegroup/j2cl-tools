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

package org.kie.j2cl.tools.di.core.internal.managed;

import java.lang.annotation.Annotation;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.BeanManager;
import org.kie.j2cl.tools.di.core.internal.InstanceImpl;
import org.kie.j2cl.tools.di.core.ioc.ContextualTypeProvider;
import org.kie.j2cl.tools.di.core.ioc.IOCProvider;

@IOCProvider
public class InstanceContextualTypeProvider implements ContextualTypeProvider<Instance> {

  private final BeanManager manager;

  @Inject
  public InstanceContextualTypeProvider(BeanManager manager) {
    this.manager = manager;
  }

  @Override
  public Instance provide(Class<?>[] typeargs, Annotation[] qualifiers) {
    Class clazz = typeargs[0];
    return new InstanceImpl(manager, clazz, qualifiers);
  }
}
