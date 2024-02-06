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

package org.kie.j2cl.tools.yaml.tests.beans;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(PersonTest.class)
public class PersonTest extends AbstractYamlTest<Person> {

  private final String YAML =
      "firstName: AAA"
          + "\n"
          + "secondName: BBB"
          + "\n"
          + "age: 99"
          + "\n"
          + "address:"
          + "\n"
          + "  country: CCC"
          + "\n"
          + "  city: DDD"
          + "\n"
          + "  street: EEE";

  private static final Person_YamlMapperImpl mapper = Person_YamlMapperImpl.INSTANCE;

  public PersonTest() {
    super(mapper);
  }

  private static final Person test = new Person("AAA", "BBB", 99, new Address("CCC", "DDD", "EEE"));

  @Test
  public void testSerializeValue() throws IOException {
    assertEquals(YAML, mapper.write(test));
  }

  @Test
  public void testDeserializeValue() throws IOException {
    Person temp = mapper.read(YAML);
    assertEquals(test, temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    Person person = new Person();
    person.setFirstName(null);
    person.setSecondName(null);
    person.setAge(0);
    person.setAddress(null);

    assertMarshallingAndUnmarshalling(person);
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    Person person = new Person("", "", 0, new Address("", "", ""));

    assertMarshallingAndUnmarshalling(person);
  }

  @Test
  public void testMarshallingWithValues() throws IOException {
    Person person = new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1"));

    assertMarshallingAndUnmarshalling(person);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    Person person1 = new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1"));
    Person person2 = new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1"));

    assertEquals(person1, person2);
    assertEquals(person1.hashCode(), person2.hashCode());

    assertMarshallingAndUnmarshalling(person1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    Person person1 = new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1"));
    Person person2 = new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2"));

    Assert.assertNotEquals(person1, person2);

    assertMarshallingAndUnmarshalling(person1);
    assertMarshallingAndUnmarshalling(person2);
  }
}
