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

package org.kie.j2cl.tools.yaml.tests.annotations;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlProperty;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.annotations.YamlPropertyTest.Tested;

@J2clTestInput(YamlPropertyTest.class)
public class YamlPropertyTest extends AbstractYamlTest<Tested> {

  private static final YamlPropertyTest_Tested_YamlMapperImpl mapper =
      YamlPropertyTest_Tested_YamlMapperImpl.INSTANCE;

  private final String YAML =
      "_name: zzz"
          + "\n"
          + "_array:"
          + "\n"
          + "  - aaa"
          + "\n"
          + "  - bbb"
          + "\n"
          + "  - ccc"
          + "\n"
          + "_list:"
          + "\n"
          + "  - aaa"
          + "\n"
          + "  - bbb"
          + "\n"
          + "  - ccc";

  public YamlPropertyTest() {
    super(mapper);
  }

  @Test
  public void test() throws IOException {
    Tested tested = new Tested();
    tested.setName("zzz");
    tested.setArray(new String[] {"aaa", "bbb", "ccc"});
    tested.setList(Arrays.asList("aaa", "bbb", "ccc"));

    assertEquals(YAML, mapper.write(tested));
    assertEquals(tested, mapper.read(YAML));
  }

  @YAMLMapper
  public static class Tested {
    @YamlProperty("_name")
    private String name;

    @YamlProperty("_array")
    private String[] array;

    @YamlProperty("_list")
    private List<String> list;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String[] getArray() {
      return array;
    }

    public void setArray(String[] array) {
      this.array = array;
    }

    public List<String> getList() {
      return list;
    }

    public void setList(List<String> list) {
      this.list = list;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Tested tested = (Tested) o;
      return Objects.equals(name, tested.name)
          && Arrays.equals(array, tested.array)
          && Objects.equals(list, tested.list);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(name, list);
      result = 31 * result + Arrays.hashCode(array);
      return result;
    }
  }
}
