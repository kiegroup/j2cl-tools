/*
 * Copyright © 2021
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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.generic;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

@XmlJavaTypeAdapter(InterfaceBeanAdapter.class)
public class Bean {

  private ValueInterface value;

  public Bean() {}

  public Bean(BeanModel model) {
    value =
        new ValueInterface() {
          String value = model.getValue();

          @Override
          public String getValue() {
            return value;
          }

          @Override
          public void setValue(String value) {
            this.value = value;
          }
        };
  }

  public ValueInterface getValue() {
    return value;
  }

  public void setValue(ValueInterface value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Bean)) {
      return false;
    }
    Bean bean = (Bean) o;
    return Objects.equals(getValue(), bean.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }
}
