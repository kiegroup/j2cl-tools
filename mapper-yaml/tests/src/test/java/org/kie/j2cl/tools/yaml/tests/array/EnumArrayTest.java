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

package org.kie.j2cl.tools.yaml.tests.array;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.array.EnumArrayTest.EnumArrayBeanTest;
import org.kie.j2cl.tools.yaml.tests.enums.SimpleEnum;

@J2clTestInput(EnumArrayTest.class)
public class EnumArrayTest extends AbstractYamlTest<EnumArrayBeanTest> {

  private static final EnumArrayTest_EnumArrayBeanTest_YamlMapperImpl mapper =
      EnumArrayTest_EnumArrayBeanTest_YamlMapperImpl.INSTANCE;

  public EnumArrayTest() {
    super(mapper);
  }

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

  @Test
  public void testSerializeValue() throws IOException {
    EnumArrayBeanTest bean = getListBeanTest();
    String yaml = mapper.write(bean);
    assertEquals(YAML, yaml);
  }

  private EnumArrayBeanTest getListBeanTest() {
    SimpleEnum[] array =
        new SimpleEnum[] {
          SimpleEnum.ONE, SimpleEnum.TWO, SimpleEnum.THREE, SimpleEnum.FOUR, SimpleEnum.FIVE
        };
    EnumArrayBeanTest bean = new EnumArrayBeanTest();
    bean.setId(-1);
    bean.setValues(array);
    return bean;
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    EnumArrayBeanTest temp = mapper.read(YAML);
    assertEquals(getListBeanTest(), temp);
    assertEquals(YAML, mapper.write(temp));
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    EnumArrayBeanTest enumArrayBean = new EnumArrayBeanTest();
    enumArrayBean.setId(1);
    enumArrayBean.setValues(null);

    assertMarshallingAndUnmarshalling(enumArrayBean);
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    EnumArrayBeanTest enumArrayBean = new EnumArrayBeanTest();
    enumArrayBean.setId(1);
    enumArrayBean.setValues(new SimpleEnum[0]);

    assertMarshallingAndUnmarshalling(enumArrayBean);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    EnumArrayBeanTest enumArrayBean = new EnumArrayBeanTest();
    enumArrayBean.setId(1);
    enumArrayBean.setValues(new SimpleEnum[] {SimpleEnum.ONE});

    assertMarshallingAndUnmarshalling(enumArrayBean);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    EnumArrayBeanTest enumArrayBean = new EnumArrayBeanTest();
    enumArrayBean.setId(1);
    enumArrayBean.setValues(new SimpleEnum[] {SimpleEnum.ONE, SimpleEnum.TWO, SimpleEnum.THREE});

    assertMarshallingAndUnmarshalling(enumArrayBean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    EnumArrayBeanTest enumArrayBean1 = new EnumArrayBeanTest();
    enumArrayBean1.setId(1);
    enumArrayBean1.setValues(new SimpleEnum[] {SimpleEnum.ONE, SimpleEnum.TWO, SimpleEnum.THREE});

    EnumArrayBeanTest enumArrayBean2 = new EnumArrayBeanTest();
    enumArrayBean2.setId(1);
    enumArrayBean2.setValues(new SimpleEnum[] {SimpleEnum.ONE, SimpleEnum.TWO, SimpleEnum.THREE});

    assertEquals(enumArrayBean1, enumArrayBean2);
    assertEquals(enumArrayBean1.hashCode(), enumArrayBean2.hashCode());

    assertMarshallingAndUnmarshalling(enumArrayBean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    EnumArrayBeanTest enumArrayBean1 = new EnumArrayBeanTest();
    enumArrayBean1.setId(1);
    enumArrayBean1.setValues(new SimpleEnum[] {SimpleEnum.ONE, SimpleEnum.TWO, SimpleEnum.THREE});

    EnumArrayBeanTest enumArrayBean2 = new EnumArrayBeanTest();
    enumArrayBean2.setId(2);
    enumArrayBean2.setValues(new SimpleEnum[] {SimpleEnum.THREE, SimpleEnum.TWO, SimpleEnum.ONE});

    assertNotEquals(enumArrayBean1, enumArrayBean2);

    assertMarshallingAndUnmarshalling(enumArrayBean1);
    assertMarshallingAndUnmarshalling(enumArrayBean2);
  }

  @YAMLMapper
  public static class EnumArrayBeanTest {

    private int id;

    private SimpleEnum[] values;

    public SimpleEnum[] getValues() {
      return values;
    }

    public void setValues(SimpleEnum[] values) {
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
      EnumArrayBeanTest that = (EnumArrayBeanTest) o;
      return id == that.id && Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(id);
      result = 31 * result + Arrays.hashCode(values);
      return result;
    }
  }
}
