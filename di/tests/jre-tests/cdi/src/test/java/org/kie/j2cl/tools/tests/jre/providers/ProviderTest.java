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

package org.kie.j2cl.tools.tests.jre.providers;

import org.junit.Test;
import org.kie.j2cl.tools.tests.jre.AbstractTest;
import org.kie.j2cl.tools.tests.jre.providers.provider.IOCProviderBean;
import org.kie.j2cl.tools.tests.jre.providers.provider.IOCProviderHolder;

import static org.junit.Assert.assertEquals;

public class ProviderTest extends AbstractTest {

  @Test
  public void test1() {
    IOCProviderHolder holder = app.beanManager.lookupBean(IOCProviderHolder.class).getInstance();
    assertEquals(IOCProviderBean.class, holder.iocProviderBean.getClass());
    assertEquals(holder.iocProviderBean.unique, holder.iocProviderBean2.unique);
  }
}
