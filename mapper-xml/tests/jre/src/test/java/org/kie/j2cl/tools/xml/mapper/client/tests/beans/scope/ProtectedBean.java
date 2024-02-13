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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.scope;

import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class ProtectedBean {

  public String value2;
  protected String value;
  protected String value3;
  String value1;

  protected ProtectedBean() {}

  @Override
  public int hashCode() {
    return Objects.hash(value2, getValue(), value3, value1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProtectedBean)) {
      return false;
    }
    ProtectedBean that = (ProtectedBean) o;
    return Objects.equals(value2, that.value2)
        && Objects.equals(getValue(), that.getValue())
        && Objects.equals(value3, that.value3)
        && Objects.equals(value1, that.value1);
  }

  protected String getValue() {
    return value;
  }

  protected void setValue(String value) {
    this.value = value;
  }
}
