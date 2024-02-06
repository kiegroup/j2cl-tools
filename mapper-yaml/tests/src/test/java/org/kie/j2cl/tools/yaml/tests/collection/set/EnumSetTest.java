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

import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.collection.set.EnumSetTest.EnumSetBeanTest;
import org.kie.j2cl.tools.yaml.tests.enums.SimpleEnum;

@J2clTestInput(EnumSetTest.class)
public class EnumSetTest extends AbstractYamlTest<EnumSetBeanTest> {

  private static final EnumSetTest_EnumSetBeanTest_YamlMapperImpl mapper =
      EnumSetTest_EnumSetBeanTest_YamlMapperImpl.INSTANCE;

  public EnumSetTest() {
    super(mapper);
  }

  private EnumSetBeanTest getSetBeanTest() {

    Set<SimpleEnum> set = new HashSet<>();
    EnumSetBeanTest bean = new EnumSetBeanTest();
    bean.setId(-1);
    bean.setValues(set);
    set.add(SimpleEnum.ONE);
    set.add(SimpleEnum.TWO);
    set.add(SimpleEnum.THREE);
    set.add(SimpleEnum.FOUR);
    set.add(SimpleEnum.FIVE);
    return bean;
  }

  @Test
  public void testSerializeDeSerializeValue() throws IOException {
    EnumSetBeanTest temp = mapper.read(mapper.write(getSetBeanTest()));
    assertTrue(temp.getValues().contains(SimpleEnum.ONE));
    assertTrue(temp.getValues().contains(SimpleEnum.TWO));
    assertTrue(temp.getValues().contains(SimpleEnum.THREE));
    assertTrue(temp.getValues().contains(SimpleEnum.FOUR));
    assertTrue(temp.getValues().contains(SimpleEnum.FIVE));
  }

  @YAMLMapper
  public static class EnumSetBeanTest {

    private int id;

    private Set<SimpleEnum> values;

    public Set<SimpleEnum> getValues() {
      return values;
    }

    public void setValues(Set<SimpleEnum> values) {
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
      EnumSetBeanTest that = (EnumSetBeanTest) o;
      return id == that.id && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, values);
    }
  }
}
