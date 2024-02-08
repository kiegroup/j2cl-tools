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

package org.kie.j2cl.tools.tests.jre.injection.singleton;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SimpleSingletonTest {

  @Inject
  private SingletonBean fieldOne;
  @Inject
  private SingletonBean fieldTwo;
  private SingletonBean constrOne;
  private SingletonBean constrTwo;

  @Inject
  public SimpleSingletonTest(SingletonBean constrOne, SingletonBean constrTwo) {
    this.constrOne = constrOne;
    this.constrTwo = constrTwo;
  }

  public SingletonBean getFieldOne() {
    return fieldOne;
  }

  public SingletonBean getFieldTwo() {
    return fieldTwo;
  }

  public SingletonBean getConstrOne() {
    return constrOne;
  }

  public SingletonBean getConstrTwo() {
    return constrTwo;
  }
}
