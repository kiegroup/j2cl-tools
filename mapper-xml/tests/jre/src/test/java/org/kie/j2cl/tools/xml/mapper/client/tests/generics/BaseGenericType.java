/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.j2cl.tools.xml.mapper.client.tests.generics;

public abstract class BaseGenericType<T, V> {

  private T field1;
  private V field2;

  public T getField1() {
    return field1;
  }

  public void setField1(T field1) {
    this.field1 = field1;
  }

  public V getField2() {
    return field2;
  }

  public void setField2(V field2) {
    this.field2 = field2;
  }
}
