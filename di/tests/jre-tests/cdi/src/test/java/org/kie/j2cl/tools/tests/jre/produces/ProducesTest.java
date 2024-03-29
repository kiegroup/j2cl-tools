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

package org.kie.j2cl.tools.tests.jre.produces;

import org.junit.Test;
import org.kie.j2cl.tools.tests.jre.AbstractTest;
import org.kie.j2cl.tools.tests.jre.produces.dependent.Definition;
import org.kie.j2cl.tools.tests.jre.produces.dependent.DefinitionImpl;
import org.kie.j2cl.tools.tests.jre.produces.dependent.DefinitionProducer;
import org.kie.j2cl.tools.tests.jre.produces.registry.DefaultRegistryImpl;
import org.kie.j2cl.tools.tests.jre.produces.registry.Registry;

import static org.junit.Assert.assertEquals;

public class ProducesTest extends AbstractTest {


  @Test
  public void testBeanOneImpl() {
    assertEquals(DefaultRegistryImpl.class,
        app.beanManager.lookupBean(Registry.class).getInstance().getClass());

  }

  @Test
  public void testDefinition() {
    for (int i = 0; i < 10; i++) {
      assertEquals(DefinitionProducer.class.getCanonicalName(),
          ((DefinitionImpl) app.beanManager.lookupBean(Definition.class).getInstance()).getId());

      assertEquals(DefinitionProducer.class.getCanonicalName(), ((DefinitionImpl) (app.beanManager
          .lookupBean(DefinitionProducer.class).getInstance().getDefinition())).getId());
    }


  }

}
