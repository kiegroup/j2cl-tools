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

package org.kie.j2cl.tools.yaml.tests.collection.list;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.collection.list.StringListTest.StringListBeanTest;

@J2clTestInput(StringListTest.class)
public class StringListTest extends AbstractYamlTest<StringListBeanTest> {

  private static final StringListTest_StringListBeanTest_YamlMapperImpl mapper =
      StringListTest_StringListBeanTest_YamlMapperImpl.INSTANCE;
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

  public StringListTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {

    StringListBeanTest bean = getListBeanTest();

    String yaml = mapper.write(bean);
    assertEquals(YAML, yaml);
  }

  private StringListBeanTest getListBeanTest() {

    List<String> list = new ArrayList<>();
    StringListBeanTest bean = new StringListBeanTest();
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
  public void tesDeSerializeValue() throws IOException {
    StringListBeanTest temp = mapper.read(YAML);
    assertEquals(getListBeanTest(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @YAMLMapper
  public static class StringListBeanTest {

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
      StringListBeanTest that = (StringListBeanTest) o;
      return id == that.id && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, values);
    }
  }
}
