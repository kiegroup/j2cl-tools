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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.type;

import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 6/30/20 */
@XMLMapper
@XmlType(propOrder = {"id", "firstName", "lastName"})
public class Customer {
  private String firstName;
  private String lastName;
  private int id;
  private String notInPropOrder;

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Customer)) {
      return false;
    }
    Customer customer = (Customer) o;
    return getId() == customer.getId()
        && Objects.equals(getFirstName(), customer.getFirstName())
        && Objects.equals(getLastName(), customer.getLastName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getLastName(), getId());
  }

  public String getNotInPropOrder() {
    return notInPropOrder;
  }

  public void setNotInPropOrder(String notInPropOrder) {
    this.notInPropOrder = notInPropOrder;
  }
}
