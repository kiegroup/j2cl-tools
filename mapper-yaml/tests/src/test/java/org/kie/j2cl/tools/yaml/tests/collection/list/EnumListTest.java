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
import org.kie.j2cl.tools.yaml.tests.collection.list.EnumListTest.EnumListBeanTest;
import org.kie.j2cl.tools.yaml.tests.enums.SimpleEnum;

@J2clTestInput(EnumListTest.class)
public class EnumListTest extends AbstractYamlTest<EnumListBeanTest> {

  private static final EnumListTest_EnumListBeanTest_YamlMapperImpl mapper =
      EnumListTest_EnumListBeanTest_YamlMapperImpl.INSTANCE;
  private final String YAML =
      "id: -1"
          + "\n"
          + "values:"
          + "\n"
          + "  - ONE"
          + "\n"
          + "  - TWO"
          + "\n"
          + "  - THREE"
          + "\n"
          + "  - FOUR"
          + "\n"
          + "  - FIVE";

  public EnumListTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {

    EnumListBeanTest bean = getListBeanTest();

    String yaml = mapper.write(bean);
    assertEquals(YAML, yaml);
  }

  private EnumListBeanTest getListBeanTest() {

    List<SimpleEnum> list = new ArrayList<>();
    EnumListBeanTest bean = new EnumListBeanTest();
    bean.setId(-1);
    bean.setValues(list);
    list.add(SimpleEnum.ONE);
    list.add(SimpleEnum.TWO);
    list.add(SimpleEnum.THREE);
    list.add(SimpleEnum.FOUR);
    list.add(SimpleEnum.FIVE);
    return bean;
  }

  @Test
  public void tesDeSerializeValue() throws IOException {
    EnumListBeanTest temp = mapper.read(YAML);
    assertEquals(getListBeanTest(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @YAMLMapper
  public static class EnumListBeanTest {

    private int id;

    private List<SimpleEnum> values;

    public List<SimpleEnum> getValues() {
      return values;
    }

    public void setValues(List<SimpleEnum> values) {
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
      EnumListBeanTest that = (EnumListBeanTest) o;
      return id == that.id && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, values);
    }
  }
}
