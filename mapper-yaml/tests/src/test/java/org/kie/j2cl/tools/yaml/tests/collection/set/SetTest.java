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

package org.kie.j2cl.tools.yaml.tests.collection.set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.collection.set.SetTest.SetBeanTest;

@J2clTestInput(SetTest.class)
public class SetTest extends AbstractYamlTest<SetBeanTest> {

  private static final SetTest_SetBeanTest_YamlMapperImpl mapper =
      SetTest_SetBeanTest_YamlMapperImpl.INSTANCE;
  private final String YAML =
      "id: -1"
          + "\n"
          + "values:"
          + "\n"
          + "  - firstName: AAA2"
          + "\n"
          + "    secondName: BBB2"
          + "\n"
          + "    age: 992"
          + "\n"
          + "    address:"
          + "\n"
          + "      country: CCC2"
          + "\n"
          + "      city: DDD2"
          + "\n"
          + "      street: EEE2"
          + "\n"
          + "  - firstName: AAA3"
          + "\n"
          + "    secondName: BBB3"
          + "\n"
          + "    age: 993"
          + "\n"
          + "    address:"
          + "\n"
          + "      country: CCC3"
          + "\n"
          + "      city: DDD3"
          + "\n"
          + "      street: EEE3"
          + "\n"
          + "  - firstName: AAA4"
          + "\n"
          + "    secondName: BBB4"
          + "\n"
          + "    age: 994"
          + "\n"
          + "    address:"
          + "\n"
          + "      country: CCC4"
          + "\n"
          + "      city: DDD4"
          + "\n"
          + "      street: EEE4"
          + "\n"
          + "  - firstName: AAA"
          + "\n"
          + "    secondName: BBB"
          + "\n"
          + "    age: 99"
          + "\n"
          + "    address:"
          + "\n"
          + "      country: CCC"
          + "\n"
          + "      city: DDD"
          + "\n"
          + "      street: EEE";

  public SetTest() {
    super(mapper);
  }

  private SetBeanTest getSetBeanTest() {
    Person test = new Person("AAA", "BBB", 99, new Address("CCC", "DDD", "EEE"));
    Person test2 = new Person("AAA2", "BBB2", 992, new Address("CCC2", "DDD2", "EEE2"));
    Person test3 = new Person("AAA3", "BBB3", 993, new Address("CCC3", "DDD3", "EEE3"));
    Person test4 = new Person("AAA4", "BBB4", 994, new Address("CCC4", "DDD4", "EEE4"));

    Set<Person> set = new HashSet<>();
    SetBeanTest bean = new SetBeanTest();
    bean.setId(-1);
    bean.setValues(set);
    set.add(test);
    set.add(test2);
    set.add(test3);
    set.add(test4);
    return bean;
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    SetBeanTest temp = mapper.read(YAML);
    assertEquals(getSetBeanTest(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @Test
  public void testMarshallingWithEmptySet() throws IOException {
    SetBeanTest bean = new SetBeanTest();
    bean.setId(1);
    bean.setValues(new HashSet<>());

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingWithSinglePerson() throws IOException {
    SetBeanTest bean = new SetBeanTest();
    bean.setId(1);

    Set<Person> personSet = new HashSet<>();
    personSet.add(new Person("John", "Doe", 30, new Address("USA", "New York", "123 Main St")));
    bean.setValues(personSet);

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingWithMultiplePersons() throws IOException {
    SetBeanTest bean = new SetBeanTest();
    bean.setId(1);

    Set<Person> personSet = new HashSet<>();
    personSet.add(new Person("John", "Doe", 30, new Address("USA", "New York", "123 Main St")));
    personSet.add(new Person("Jane", "Smith", 25, new Address("USA", "Los Angeles", "456 Elm St")));
    bean.setValues(personSet);

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    SetBeanTest bean1 = new SetBeanTest();
    bean1.setId(1);

    Set<Person> personSet1 = new HashSet<>();
    personSet1.add(new Person("John", "Doe", 30, new Address("USA", "New York", "123 Main St")));
    personSet1.add(
        new Person("Jane", "Smith", 25, new Address("USA", "Los Angeles", "456 Elm St")));
    bean1.setValues(personSet1);

    SetBeanTest bean2 = new SetBeanTest();
    bean2.setId(1);

    Set<Person> personSet2 = new HashSet<>();
    personSet2.add(new Person("John", "Doe", 30, new Address("USA", "New York", "123 Main St")));
    personSet2.add(
        new Person("Jane", "Smith", 25, new Address("USA", "Los Angeles", "456 Elm St")));
    bean2.setValues(personSet2);

    assertEquals(bean1, bean2);
    assertEquals(bean1.hashCode(), bean2.hashCode());

    assertMarshallingAndUnmarshalling(bean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    SetBeanTest bean1 = new SetBeanTest();
    bean1.setId(1);

    Set<Person> personSet1 = new HashSet<>();
    personSet1.add(new Person("John", "Doe", 30, new Address("USA", "New York", "123 Main St")));
    bean1.setValues(personSet1);

    SetBeanTest bean2 = new SetBeanTest();
    bean2.setId(1);

    Set<Person> personSet2 = new HashSet<>();
    personSet2.add(
        new Person("Jane", "Smith", 25, new Address("USA", "Los Angeles", "456 Elm St")));
    bean2.setValues(personSet2);

    assertNotEquals(bean1, bean2);

    assertMarshallingAndUnmarshalling(bean1);
    assertMarshallingAndUnmarshalling(bean2);
  }

  @YAMLMapper
  public static class SetBeanTest {

    private int id;

    private Set<Person> values;

    public Set<Person> getValues() {
      return values;
    }

    public void setValues(Set<Person> values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      SetBeanTest that = (SetBeanTest) o;
      return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Objects.hash(values);
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }
  }

  public static class Person {

    private String firstName;
    private String secondName;
    private int age;
    private Address address;

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getSecondName() {
      return secondName;
    }

    public void setSecondName(String secondName) {
      this.secondName = secondName;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public Address getAddress() {
      return address;
    }

    public void setAddress(Address address) {
      this.address = address;
    }

    public Person() {}

    public Person(String firstName, String secondName, int age, Address address) {
      this.firstName = firstName;
      this.secondName = secondName;
      this.age = age;
      this.address = address;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Person person = (Person) o;
      return age == person.age
          && Objects.equals(firstName, person.firstName)
          && Objects.equals(secondName, person.secondName)
          && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
      return Objects.hash(firstName, secondName, age, address);
    }
  }

  public static class Address {

    private String country;
    private String city;
    private String street;

    public Address() {}

    public Address(String country, String city, String street) {
      this.country = country;
      this.city = city;
      this.street = street;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }

    public String getCountry() {
      return country;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Address address = (Address) o;
      return Objects.equals(country, address.country)
          && Objects.equals(city, address.city)
          && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
      return Objects.hash(country, city, street);
    }

    public void setCountry(String country) {
      this.country = country;
    }
  }
}
