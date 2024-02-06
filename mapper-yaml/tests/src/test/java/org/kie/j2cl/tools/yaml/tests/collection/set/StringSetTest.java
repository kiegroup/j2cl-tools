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

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.collection.set.StringSetTest.StringSetBeanTest;

@J2clTestInput(StringSetTest.class)
public class StringSetTest extends AbstractYamlTest<StringSetBeanTest> {

  private static final StringSetTest_StringSetBeanTest_YamlMapperImpl mapper =
      StringSetTest_StringSetBeanTest_YamlMapperImpl.INSTANCE;

  public StringSetTest() {
    super(mapper);
  }

  private final String YAML =
      "id: -1"
          + "\n"
          + "values:"
          + "\n"
          + "  - AAA"
          + "\n"
          + "  - AAA1"
          + "\n"
          + "  - AAA2"
          + "\n"
          + "  - AAA3"
          + "\n"
          + "  - AAA4";

  @Test
  public void testSerializeValue() throws IOException {

    StringSetBeanTest bean = getListBeanTest();

    String yaml = mapper.write(bean);
    assertEquals(YAML, yaml);
  }

  private StringSetBeanTest getListBeanTest() {

    List<String> list = new ArrayList<>();
    StringSetBeanTest bean = new StringSetBeanTest();
    bean.setId(-1);
    bean.setValues(list);
    list.add("AAA");
    list.add("AAA1");
    list.add("AAA2");
    list.add("AAA3");
    list.add("AAA4");
    return bean;
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    StringSetBeanTest temp = mapper.read(YAML);
    assertEquals(getListBeanTest(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @Test
  public void testMarshallingWithEmptyList() throws IOException {
    StringSetBeanTest bean = new StringSetBeanTest();
    bean.setId(1);
    bean.setValues(new ArrayList<>());

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    StringSetBeanTest bean = new StringSetBeanTest();
    bean.setId(1);
    bean.setValues(Arrays.asList("Value1"));

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    StringSetBeanTest bean = new StringSetBeanTest();
    bean.setId(1);
    bean.setValues(Arrays.asList("Value1", "Value2", "Value3"));

    assertMarshallingAndUnmarshalling(bean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    StringSetBeanTest bean1 = new StringSetBeanTest();
    bean1.setId(1);
    bean1.setValues(Arrays.asList("Value1", "Value2"));

    StringSetBeanTest bean2 = new StringSetBeanTest();
    bean2.setId(1);
    bean2.setValues(Arrays.asList("Value1", "Value2"));

    Assert.assertEquals(bean1, bean2);
    Assert.assertEquals(bean1.hashCode(), bean2.hashCode());

    assertMarshallingAndUnmarshalling(bean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    StringSetBeanTest bean1 = new StringSetBeanTest();
    bean1.setId(1);
    bean1.setValues(Arrays.asList("Value1", "Value2"));

    StringSetBeanTest bean2 = new StringSetBeanTest();
    bean2.setId(1);
    bean2.setValues(Arrays.asList("Value1", "Value3"));

    Assert.assertNotEquals(bean1, bean2);

    assertMarshallingAndUnmarshalling(bean1);
    assertMarshallingAndUnmarshalling(bean2);
  }

  @YAMLMapper
  public static class StringSetBeanTest {

    private int id;

    private List<String> values;

    public List<String> getValues() {
      return values;
    }

    public void setValues(List<String> values) {
      this.values = values;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      StringSetBeanTest that = (StringSetBeanTest) o;
      return id == that.id && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, values);
    }
  }
}
