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

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(BeanWithSeveralPropsTest.class)
public class BeanWithSeveralPropsTest extends AbstractYamlTest<BeanWithSeveralPropsTest.Tested> {

  private static final BeanWithSeveralPropsTest_Tested_YamlMapperImpl mapper =
      BeanWithSeveralPropsTest_Tested_YamlMapperImpl.INSTANCE;

  public BeanWithSeveralPropsTest() {
    super(mapper);
  }

  @Test
  public void test() throws IOException {
    Tested tested = new Tested();
    tested.setHolders(
        new Holder[] {
          new Holder(new String[] {"arr1", "arr2", "arr3"}, "test1"),
          new Holder(new String[] {"arr1", "arr2", "arr3"}, "test1")
        });
    String yaml = mapper.write(tested);
    String expected =
        "holders:"
            + "\n"
            + "  - array:"
            + "\n"
            + "      - arr1"
            + "\n"
            + "      - arr2"
            + "\n"
            + "      - arr3"
            + "\n"
            + "    value: test1"
            + "\n"
            + "  - array:"
            + "\n"
            + "      - arr1"
            + "\n"
            + "      - arr2"
            + "\n"
            + "      - arr3"
            + "\n"
            + "    value: test1";

    assertEquals(expected, yaml);
    assertEquals(tested, mapper.read(yaml));
  }

  @YAMLMapper
  public static class Tested {

    private Holder[] holders;

    public Holder[] getHolders() {
      return holders;
    }

    public void setHolders(Holder[] holders) {
      this.holders = holders;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Tested tested = (Tested) o;
      return Arrays.equals(holders, tested.holders);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(holders);
    }
  }

  public static class Holder {

    private String[] array;

    private String value;

    public Holder() {}

    public Holder(String[] values, String value) {
      this.array = values;
      this.value = value;
    }

    public String[] getArray() {
      return array;
    }

    public void setArray(String[] array) {
      this.array = array;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Holder holder = (Holder) o;
      return Arrays.equals(array, holder.array) && Objects.equals(value, holder.value);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(value);
      result = 31 * result + Arrays.hashCode(array);
      return result;
    }
  }
}
