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
import org.kie.j2cl.tools.tests.jre.injection.generic.TypeOneGenericAbstractBean;
import org.kie.j2cl.tools.tests.jre.injection.generic.TypeOneGenericBeansHolder;
import org.kie.j2cl.tools.tests.jre.injection.generic.TypeTwoGenericAbstractBean;
import org.kie.j2cl.tools.tests.jre.injection.generic.TypeTwoGenericBeansHolder;

import static org.junit.Assert.assertEquals;

public class GenericBeanInjectionTest extends AbstractTest {

  @Test
  public void testTypeOne() {
    assertEquals(TypeOneGenericAbstractBean.class, app.beanManager
        .lookupBean(TypeOneGenericBeansHolder.class).getInstance().tGenericAbstractBean.getClass());
  }

  @Test
  public void testTypeTwo() {
    assertEquals(TypeTwoGenericAbstractBean.class, app.beanManager
        .lookupBean(TypeTwoGenericBeansHolder.class).getInstance().tGenericAbstractBean.getClass());
  }
}
