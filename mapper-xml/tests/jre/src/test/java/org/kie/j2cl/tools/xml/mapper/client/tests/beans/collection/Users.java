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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Address;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Person;

/** @author Dmitrii Tikhomirov Created by treblereel 3/30/20 */
@XMLMapper
public class Users {

  private Map<String, Person> activeUsers;

  @XmlElement(name = "all_users", namespace = "do")
  private List<Person> allUsers;

  private Map<Person, Address> addressMap;

  private transient Iterable<Address> address;

  @Override
  public int hashCode() {
    return Objects.hash(activeUsers, address, allUsers);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Users)) {
      return false;
    }
    Users users = (Users) o;
    return Objects.equals(activeUsers, users.activeUsers)
        && Objects.equals(addressMap, users.addressMap)
        && Objects.equals(allUsers, users.allUsers)
        && Objects.equals(address, users.address);
  }

  public Map<String, Person> getActiveUsers() {
    return activeUsers;
  }

  public void setActiveUsers(Map<String, Person> activeUsers) {
    this.activeUsers = activeUsers;
  }

  public Iterable<Address> getAddress() {
    return address;
  }

  public void setAddress(Iterable<Address> address) {
    this.address = address;
  }

  public Map<Person, Address> getAddressMap() {
    return addressMap;
  }

  public void setAddressMap(Map<Person, Address> addressMap) {
    this.addressMap = addressMap;
  }

  public List<Person> getAllUsers() {
    return allUsers;
  }

  public void setAllUsers(List<Person> allUsers) {
    this.allUsers = allUsers;
  }

  public enum TYPE {
    ONE,
    TWO;
  }
}
