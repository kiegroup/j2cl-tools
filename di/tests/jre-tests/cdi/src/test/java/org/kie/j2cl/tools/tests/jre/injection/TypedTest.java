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

package org.kie.j2cl.tools.tests.jre.injection;

import org.junit.Test;
import org.kie.j2cl.tools.tests.jre.AbstractTest;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.typed.AbstractCanvasHandler;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.typed.ApplicationCommandManager;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.typed.RegistryAwareCommandManager;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.typed.SessionCommandManager;
import org.kie.j2cl.tools.tests.jre.injection.typed.case2.BeanOne;
import org.kie.j2cl.tools.tests.jre.injection.typed.case2.BeanTwo;
import org.kie.j2cl.tools.tests.jre.injection.typed.case2.Iface;

import static org.junit.Assert.assertEquals;

public class TypedTest extends AbstractTest {

  @Test
  public void testQualifierFieldInjectionBean() {
    assertEquals(ApplicationCommandManager.class, app.beanManager.lookupBean(SessionCommandManager.class).getInstance().getClass());

    SessionCommandManager<AbstractCanvasHandler> sessionCommandManager =
        app.qualifierFieldInjection.morphNodeToolboxAction.sessionCommandManager;

    assertEquals(ApplicationCommandManager.class, sessionCommandManager.getClass());

    assertEquals(RegistryAwareCommandManager.class,
        ((ApplicationCommandManager) sessionCommandManager).commandManagerInstances.getClass());
  }

  @Test
  public void testDefaultDefinitionsCacheRegistry() {
    assertEquals(RegistryAwareCommandManager.class,
        app.beanManager.lookupBean(RegistryAwareCommandManager.class).getInstance().getClass());
  }

  @Test
  public void testDefaultAndTyped() {
    assertEquals(BeanTwo.class, app.beanManager.lookupBean(Iface.class).getInstance().getClass());
    assertEquals(BeanOne.class, app.beanManager.lookupBean(BeanOne.class).getInstance().getClass());
  }

}
