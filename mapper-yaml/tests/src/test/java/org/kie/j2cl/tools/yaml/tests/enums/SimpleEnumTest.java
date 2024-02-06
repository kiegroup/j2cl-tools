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

package org.kie.j2cl.tools.yaml.tests.enums;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.enums.SimpleEnumTest.SimpleEnumTestBean;

@J2clTestInput(SimpleEnumTest.class)
public class SimpleEnumTest extends AbstractYamlTest<SimpleEnumTestBean> {

  private static final SimpleEnumTest_SimpleEnumTestBean_YamlMapperImpl mapper =
      SimpleEnumTest_SimpleEnumTestBean_YamlMapperImpl.INSTANCE;

  public SimpleEnumTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingEnumValueOne() throws IOException {
    SimpleEnumTestBean enumTestBean = new SimpleEnumTestBean();
    enumTestBean.setValue(SimpleEnum.ONE);

    assertMarshallingAndUnmarshalling(enumTestBean);
  }

  @Test
  public void testMarshallingEnumValueTwo() throws IOException {
    SimpleEnumTestBean enumTestBean = new SimpleEnumTestBean();
    enumTestBean.setValue(SimpleEnum.TWO);

    assertMarshallingAndUnmarshalling(enumTestBean);
  }

  @Test
  public void testMarshallingEnumValueThree() throws IOException {
    SimpleEnumTestBean enumTestBean = new SimpleEnumTestBean();
    enumTestBean.setValue(SimpleEnum.THREE);

    assertMarshallingAndUnmarshalling(enumTestBean);
  }

  @Test
  public void testMarshallingEnumValueFour() throws IOException {
    SimpleEnumTestBean enumTestBean = new SimpleEnumTestBean();
    enumTestBean.setValue(SimpleEnum.FOUR);

    assertMarshallingAndUnmarshalling(enumTestBean);
  }

  @Test
  public void testMarshallingEnumValueFive() throws IOException {
    SimpleEnumTestBean enumTestBean = new SimpleEnumTestBean();
    enumTestBean.setValue(SimpleEnum.FIVE);

    assertMarshallingAndUnmarshalling(enumTestBean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    SimpleEnumTestBean enumTestBean1 = new SimpleEnumTestBean();
    enumTestBean1.setValue(SimpleEnum.ONE);

    SimpleEnumTestBean enumTestBean2 = new SimpleEnumTestBean();
    enumTestBean2.setValue(SimpleEnum.ONE);

    Assert.assertEquals(enumTestBean1, enumTestBean2);
    Assert.assertEquals(enumTestBean1.hashCode(), enumTestBean2.hashCode());

    assertMarshallingAndUnmarshalling(enumTestBean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    SimpleEnumTestBean enumTestBean1 = new SimpleEnumTestBean();
    enumTestBean1.setValue(SimpleEnum.ONE);

    SimpleEnumTestBean enumTestBean2 = new SimpleEnumTestBean();
    enumTestBean2.setValue(SimpleEnum.TWO);

    Assert.assertNotEquals(enumTestBean1, enumTestBean2);

    assertMarshallingAndUnmarshalling(enumTestBean1);
    assertMarshallingAndUnmarshalling(enumTestBean2);
  }

  @YAMLMapper
  public static class SimpleEnumTestBean {
    private SimpleEnum value;

    public SimpleEnum getValue() {
      return value;
    }

    public void setValue(SimpleEnum value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      SimpleEnumTestBean that = (SimpleEnumTestBean) o;
      return value == that.value;
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
