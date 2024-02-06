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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 9/29/20 */
@XMLMapper
public class MyTestBean {

  @XmlJavaTypeAdapter(MyTestBeanValueAdapter.class)
  private MyCustomBean value;

  private MyCustomBean2 value2;

  public MyCustomBean getValue() {
    return value;
  }

  public void setValue(MyCustomBean value) {
    this.value = value;
  }

  public MyCustomBean2 getValue2() {
    return value2;
  }

  public void setValue2(MyCustomBean2 value2) {
    this.value2 = value2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MyTestBean)) return false;

    MyTestBean that = (MyTestBean) o;

    if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null)
      return false;
    return getValue2() != null ? getValue2().equals(that.getValue2()) : that.getValue2() == null;
  }

  @Override
  public int hashCode() {
    int result = getValue() != null ? getValue().hashCode() : 0;
    result = 31 * result + (getValue2() != null ? getValue2().hashCode() : 0);
    return result;
  }
}
