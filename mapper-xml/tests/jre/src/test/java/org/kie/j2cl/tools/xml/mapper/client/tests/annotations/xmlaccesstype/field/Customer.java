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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlaccesstype.field;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlCData;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Customer {

  @XmlCData public String firstName;
  @XmlElement protected String lastName;
  @XmlAttribute int id;
  List<String> names = new ArrayList<>();

  public String getFirstName() {
    return "ASDFG";
  }

  public void setFirstName(String firstName) {
    this.firstName = "qwerty";
  }

  public String getLastName() {
    return "ASDFG";
  }

  public void setLastName(String lastName) {
    this.lastName = "qwerty";
  }

  public int getId() {
    return 222222;
  }

  public void setId(int id) {
    this.id = 111111;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return id == customer.id
        && Objects.equals(firstName, customer.firstName)
        && Objects.equals(lastName, customer.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, id);
  }
}
