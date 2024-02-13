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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test4;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.omg.org/bpmn30")
public class NSTest2 {

  private String value2;

  @XmlAttribute private String value3;

  @XmlAttribute(namespace = "http://www.omg.org/bpmn40")
  private String value4;

  @XmlElement(namespace = "http://www.omg.org/bpmn40")
  private String value5;

  @XmlElement(namespace = "http://www.omg.org/bpmn20")
  private String value6;

  public String getValue2() {
    return value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }

  public String getValue3() {
    return value3;
  }

  public void setValue3(String value3) {
    this.value3 = value3;
  }

  public String getValue4() {
    return value4;
  }

  public void setValue4(String value4) {
    this.value4 = value4;
  }

  public String getValue5() {
    return value5;
  }

  public void setValue5(String value5) {
    this.value5 = value5;
  }

  public String getValue6() {
    return value6;
  }

  public void setValue6(String value6) {
    this.value6 = value6;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NSTest2)) return false;

    NSTest2 nsTest2 = (NSTest2) o;

    if (getValue2() != null
        ? !getValue2().equals(nsTest2.getValue2())
        : nsTest2.getValue2() != null) return false;
    if (getValue3() != null
        ? !getValue3().equals(nsTest2.getValue3())
        : nsTest2.getValue3() != null) return false;
    if (getValue4() != null
        ? !getValue4().equals(nsTest2.getValue4())
        : nsTest2.getValue4() != null) return false;
    if (getValue5() != null
        ? !getValue5().equals(nsTest2.getValue5())
        : nsTest2.getValue5() != null) return false;
    return getValue6() != null
        ? getValue6().equals(nsTest2.getValue6())
        : nsTest2.getValue6() == null;
  }

  @Override
  public int hashCode() {
    int result = getValue2() != null ? getValue2().hashCode() : 0;
    result = 31 * result + (getValue3() != null ? getValue3().hashCode() : 0);
    result = 31 * result + (getValue4() != null ? getValue4().hashCode() : 0);
    result = 31 * result + (getValue5() != null ? getValue5().hashCode() : 0);
    result = 31 * result + (getValue6() != null ? getValue6().hashCode() : 0);
    return result;
  }
}
