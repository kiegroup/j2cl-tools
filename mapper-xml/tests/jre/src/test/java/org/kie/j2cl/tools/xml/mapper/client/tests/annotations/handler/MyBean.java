/*
 * Copyright © 2020 Treblereel
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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler;

import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlTypeAdapter;

/** @author Dmitrii Tikhomirov Created by treblereel 5/19/20 */
@XmlTypeAdapter(serializer = MyBeanMarshaller.class, deserializer = MyBeanDemarshaller.class)
public class MyBean {

  private String value;

  private String value2;

  public MyBean() {}

  public MyBean(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue2() {
    return value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MyBean)) {
      return false;
    }
    MyBean myBean = (MyBean) o;
    return Objects.equals(getValue(), myBean.getValue())
        && Objects.equals(getValue2(), myBean.getValue2());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue(), getValue2());
  }
}
