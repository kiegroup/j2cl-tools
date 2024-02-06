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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.beans.ArrayPersonTest.ArrayPersonBean;

@J2clTestInput(ArrayPersonTest.class)
public class ArrayPersonTest extends AbstractYamlTest<ArrayPersonBean> {

  private final String YAML =
      "values:"
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
          + "      street: EEE"
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
          + "      street: EEE4";

  private static final ArrayPersonTest_ArrayPersonBean_YamlMapperImpl mapper =
      ArrayPersonTest_ArrayPersonBean_YamlMapperImpl.INSTANCE;

  public ArrayPersonTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {
    ArrayPersonBean bean = genArrayPersonBean();
    String yaml = mapper.write(bean);
    assertEquals(YAML, yaml);
  }

  @Test
  public void testDeserializeValue() throws IOException {
    ArrayPersonBean temp = mapper.read(YAML);
    assertEquals(genArrayPersonBean(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  private ArrayPersonBean genArrayPersonBean() {
    Person test = new Person("AAA", "BBB", 99, new Address("CCC", "DDD", "EEE"));
    Person test2 = new Person("AAA2", "BBB2", 992, new Address("CCC2", "DDD2", "EEE2"));
    Person test3 = new Person("AAA3", "BBB3", 993, new Address("CCC3", "DDD3", "EEE3"));
    Person test4 = new Person("AAA4", "BBB4", 994, new Address("CCC4", "DDD4", "EEE4"));

    Person[] values = new Person[] {test, test2, test3, test4};

    ArrayPersonBean bean = new ArrayPersonBean();
    bean.setValues(values);

    return bean;
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    ArrayPersonBean arrayPersonBean = new ArrayPersonBean();
    arrayPersonBean.setValues(null);

    assertNull(mapper.read(mapper.write(arrayPersonBean)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    ArrayPersonBean arrayPersonBean = new ArrayPersonBean();
    arrayPersonBean.setValues(new Person[0]);

    assertMarshallingAndUnmarshalling(arrayPersonBean);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    ArrayPersonBean arrayPersonBean = new ArrayPersonBean();
    arrayPersonBean.setValues(
        new Person[] {new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1"))});

    assertMarshallingAndUnmarshalling(arrayPersonBean);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    ArrayPersonBean arrayPersonBean = new ArrayPersonBean();
    arrayPersonBean.setValues(
        new Person[] {
          new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1")),
          new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2")),
          new Person("Mark", "Johnson", 35, new Address("Canada", "Toronto", "Street 3"))
        });

    assertMarshallingAndUnmarshalling(arrayPersonBean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    ArrayPersonBean arrayPersonBean1 = new ArrayPersonBean();
    arrayPersonBean1.setValues(
        new Person[] {
          new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1")),
          new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2")),
          new Person("Mark", "Johnson", 35, new Address("Canada", "Toronto", "Street 3"))
        });

    ArrayPersonBean arrayPersonBean2 = new ArrayPersonBean();
    arrayPersonBean2.setValues(
        new Person[] {
          new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1")),
          new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2")),
          new Person("Mark", "Johnson", 35, new Address("Canada", "Toronto", "Street 3"))
        });

    assertEquals(arrayPersonBean1, arrayPersonBean2);
    assertEquals(arrayPersonBean1.hashCode(), arrayPersonBean2.hashCode());

    assertMarshallingAndUnmarshalling(arrayPersonBean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    ArrayPersonBean arrayPersonBean1 = new ArrayPersonBean();
    arrayPersonBean1.setValues(
        new Person[] {
          new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1")),
          new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2")),
          new Person("Mark", "Johnson", 35, new Address("Canada", "Toronto", "Street 3"))
        });

    ArrayPersonBean arrayPersonBean2 = new ArrayPersonBean();
    arrayPersonBean2.setValues(
        new Person[] {
          new Person("John", "Doe", 30, new Address("USA", "New York", "Street 1")),
          new Person("Jane", "Smith", 25, new Address("UK", "London", "Street 2")),
          new Person("Mark", "Johnson", 36, new Address("Canada", "Toronto", "Street 3"))
        });

    assertNotEquals(arrayPersonBean1, arrayPersonBean2);

    assertMarshallingAndUnmarshalling(arrayPersonBean1);
    assertMarshallingAndUnmarshalling(arrayPersonBean2);
  }

  @YAMLMapper
  public static class ArrayPersonBean {

    private Person[] values;

    public Person[] getValues() {
      return values;
    }

    public void setValues(Person[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ArrayPersonBean that = (ArrayPersonBean) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
