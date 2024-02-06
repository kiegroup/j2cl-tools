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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans;

import java.util.Objects;

public class Address {

  private int id;
  private String street = "Street";
  private String city = "Los Alamos";

  public Address() {}

  public Address(int id, String street, String city) {
    this.id = id;
    this.street = street;
    this.city = city;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Address)) {
      return false;
    }
    Address address = (Address) o;
    return getId() == address.getId()
        && Objects.equals(getStreet(), address.getStreet())
        && Objects.equals(getCity(), address.getCity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStreet(), getCity());
  }

  @Override
  public String toString() {
    return "Address{" + "id=" + id + ", street='" + street + '\'' + ", city='" + city + '\'' + '}';
  }
}
