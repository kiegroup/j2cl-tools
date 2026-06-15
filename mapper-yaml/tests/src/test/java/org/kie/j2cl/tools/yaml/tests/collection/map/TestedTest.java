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

package org.kie.j2cl.tools.yaml.tests.collection.map;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.tests.collection.list.StringListTest;

@J2clTestInput(StringListTest.class)
public class TestedTest {

  private static final Tested_YamlMapperImpl mapper = new Tested_YamlMapperImpl();

  @Test
  public void test() throws IOException {
    String yaml_as_string = "map:\n" + "  key1: value1\n" + "  key2: value2";

    Tested tested = new Tested();
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    tested.setMap(map);

    String yaml = mapper.write(tested);

    assertEquals(yaml_as_string, yaml);
    assertEquals(yaml_as_string, mapper.write(mapper.read(yaml)));
  }

  @Test
  public void test2() throws IOException {

    String yaml_as_string =
        "map:\n"
            + "  key1: value1\n"
            + "  key2: value2\n"
            + "valueHolderMap:\n"
            + "  key1:\n"
            + "    value: value1\n"
            + "  key2:\n"
            + "    value: value2";

    Tested tested = new Tested();
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    tested.setMap(map);

    Map<String, ValueHolder> valueHolderMap = new HashMap<>();
    valueHolderMap.put("key1", new ValueHolder("value1"));
    valueHolderMap.put("key2", new ValueHolder("value2"));
    tested.setValueHolderMap(valueHolderMap);

    String yaml = mapper.write(tested);

    assertEquals(yaml_as_string, yaml);
    assertEquals(yaml_as_string, mapper.write(mapper.read(yaml_as_string)));
  }

  @Test
  public void test3() throws IOException {
    String yaml_as_string =
        "map:\n"
            + "  key1: value1\n"
            + "  key2: value2\n"
            + "valueHolderMap:\n"
            + "  key1:\n"
            + "    value: value1\n"
            + "  key2:\n"
            + "    value: value2\n"
            + "listMap:\n"
            + "  key1:\n"
            + "    - value1\n"
            + "    - value2\n"
            + "  key2:\n"
            + "    - value3\n"
            + "    - value4";

    Tested tested = new Tested();
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    tested.setMap(map);

    Map<String, ValueHolder> valueHolderMap = new HashMap<>();
    valueHolderMap.put("key1", new ValueHolder("value1"));
    valueHolderMap.put("key2", new ValueHolder("value2"));
    tested.setValueHolderMap(valueHolderMap);

    Map<String, List<String>> listMap = new HashMap<>();
    listMap.put("key1", List.of("value1", "value2"));
    listMap.put("key2", List.of("value3", "value4"));
    tested.setListMap(listMap);

    String yaml = mapper.write(tested);

    assertEquals(yaml_as_string, yaml);
    assertEquals(yaml_as_string, mapper.write(mapper.read(yaml_as_string)));
  }
}
