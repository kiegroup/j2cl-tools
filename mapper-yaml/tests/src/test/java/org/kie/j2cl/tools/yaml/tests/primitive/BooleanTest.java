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

package org.kie.j2cl.tools.yaml.tests.primitive;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(BooleanTest.class)
public class BooleanTest extends AbstractYamlTest<BooleanTest.BooleanBean> {

  private static final String YAML_TRUE = "check: true";
  private static final String YAML_FALSE = "check: false";

  private static BooleanTest_BooleanBean_YamlMapperImpl mapper =
      BooleanTest_BooleanBean_YamlMapperImpl.INSTANCE;

  public BooleanTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {
    BooleanBean test = new BooleanBean();
    assertEquals(YAML_FALSE, mapper.write(test));
    test.setCheck(true);
    assertEquals(YAML_TRUE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck(false);
    assertEquals(YAML_FALSE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testDeserializeValue() throws IOException {
    assertTrue(mapper.read(YAML_TRUE).isCheck());
    assertFalse(mapper.read(YAML_FALSE).isCheck());
    BooleanBean test = new BooleanBean();
    test.setCheck(true);
    assertEquals(YAML_TRUE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck(false);
    assertEquals(YAML_FALSE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testMarshallingTrueValue() throws IOException {
    BooleanBean booleanBean = new BooleanBean();
    booleanBean.setCheck(true);

    assertMarshallingAndUnmarshalling(booleanBean);
  }

  @Test
  public void testMarshallingFalseValue() throws IOException {
    BooleanBean booleanBean = new BooleanBean();
    booleanBean.setCheck(false);

    assertMarshallingAndUnmarshalling(booleanBean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    BooleanBean booleanBean1 = new BooleanBean();
    booleanBean1.setCheck(true);

    BooleanBean booleanBean2 = new BooleanBean();
    booleanBean2.setCheck(true);

    assertEquals(booleanBean1, booleanBean2);
    assertEquals(booleanBean1.hashCode(), booleanBean2.hashCode());

    assertMarshallingAndUnmarshalling(booleanBean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    BooleanBean booleanBean1 = new BooleanBean();
    booleanBean1.setCheck(true);

    BooleanBean booleanBean2 = new BooleanBean();
    booleanBean2.setCheck(false);

    assertNotEquals(booleanBean1, booleanBean2);

    assertMarshallingAndUnmarshalling(booleanBean1);
    assertMarshallingAndUnmarshalling(booleanBean2);
  }

  @YAMLMapper
  public static class BooleanBean {

    private boolean check;

    public boolean isCheck() {
      return check;
    }

    public void setCheck(boolean check) {
      this.check = check;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof BooleanBean)) {
        return false;
      }
      BooleanBean that = (BooleanBean) o;
      return isCheck() == that.isCheck();
    }

    @Override
    public int hashCode() {
      return Objects.hash(isCheck());
    }
  }
}
