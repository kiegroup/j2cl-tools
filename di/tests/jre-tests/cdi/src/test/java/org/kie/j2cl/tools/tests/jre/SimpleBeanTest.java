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

package org.kie.j2cl.tools.tests.jre;

import org.junit.Test;
import org.kie.j2cl.tools.tests.jre.injection.applicationscoped.SimpleBeanApplicationScoped;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.QualifierBeanOne;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.QualifierBeanTwo;
import org.kie.j2cl.tools.tests.jre.injection.qualifiers.QualifierConstructorInjection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimpleBeanTest extends AbstractTest {

  @Test
  public void testAppSimpleBean() {
    assertNotNull(app.getSimpleBeanApplicationScoped());
    assertEquals(SimpleBeanApplicationScoped.class.getSimpleName(),
        app.getSimpleBeanApplicationScoped().getName());

    assertNotNull(app.getQualifierConstructorInjection());
    assertEquals(QualifierConstructorInjection.class.getSimpleName(),
        app.getQualifierConstructorInjection().getClass().getSimpleName());
    assertEquals(QualifierBeanOne.class,
        app.getQualifierConstructorInjection().qualifierBeanOne.getClass());
    assertEquals(QualifierBeanTwo.class,
        app.getQualifierConstructorInjection().qualifierBeanTwo.getClass());
  }
}
