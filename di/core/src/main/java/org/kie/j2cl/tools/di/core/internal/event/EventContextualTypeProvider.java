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

package org.kie.j2cl.tools.di.core.internal.event;

import java.lang.annotation.Annotation;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import org.kie.j2cl.tools.di.core.ioc.ContextualTypeProvider;
import org.kie.j2cl.tools.di.core.ioc.IOCProvider;

@IOCProvider
public class EventContextualTypeProvider implements ContextualTypeProvider<Event> {
  private final EventManager eventManager;

  @Inject
  public EventContextualTypeProvider(EventManager manager) {
    this.eventManager = manager;
  }

  @Override
  public Event provide(Class<?>[] typeargs, Annotation[] qualifiers) {
    Class clazz = typeargs[0];
    return eventManager.get(clazz);
  }
}
