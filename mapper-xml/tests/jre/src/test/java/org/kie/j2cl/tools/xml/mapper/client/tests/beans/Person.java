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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class Person {

  private String firstName;
  private String lastName;

  private Date birthday = new Date(1234567890);

  private Timestamp alive = new Timestamp(1);

  private Address address;

  private List<Person> childs;

  @Override
  public int hashCode() {
    return Objects.hash(
        getFirstName(), getLastName(), getBirthday(), getAlive(), getAddress(), getChilds());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(getFirstName(), person.getFirstName())
        && Objects.equals(getLastName(), person.getLastName())
        && Objects.equals(getBirthday(), person.getBirthday())
        && Objects.equals(getAlive(), person.getAlive())
        && Objects.equals(getAddress(), person.getAddress())
        && Objects.equals(getChilds(), person.getChilds());
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Timestamp getAlive() {
    return alive;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<Person> getChilds() {
    return childs;
  }

  public void setChilds(List<Person> childs) {
    this.childs = childs;
  }

  public void setAlive(Timestamp alive) {
    this.alive = alive;
  }

  @Override
  public String toString() {
    return "Person{" + "firstName='" + firstName + '\'' + '}';
  }
}
