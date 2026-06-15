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

package org.kie.j2cl.tools.yaml.tests.annotations.customtypeser;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

@J2clTestInput(YamlTypeSerializerWithAnnotatedTypeTest.class)
public class YamlTypeSerializerWithAnnotatedTypeTest {

  private static final Tested_YamlMapperImpl mapper = Tested_YamlMapperImpl.INSTANCE;

  private static final String YAML =
      "value: ZZZ\n"
          + "values:\n"
          + "  - '111'\n"
          + "  - '222'\n"
          + "listOfValues:\n"
          + "  - '333'\n"
          + "  - '444'\n"
          + "oneMoreValueHolder: BLA-BLA\n"
          + "oneMoreValueHolders:\n"
          + "  - XXX\n"
          + "  - YYY\n"
          + "listOfOneMoreValueHolders:\n"
          + "  - AAA\n"
          + "  - BBB";

  @Test
  public void test() {
    Tested tested = new Tested();
    assertEquals("", mapper.write(tested));
  }

  @Test
  public void test2() throws IOException {
    Tested tested = new Tested();
    tested.setValues(new ValueHolder[] {new ValueHolder("111"), new ValueHolder("222")});
    tested.setListOfValues(Arrays.asList(new ValueHolder("333"), new ValueHolder("444")));
    tested.setValue(new ValueHolder("zzz"));

    tested.setOneMoreValueHolder(new OneMoreValueHolder("bla-bla"));
    tested.setOneMoreValueHolders(
        new OneMoreValueHolder[] {new OneMoreValueHolder("xxx"), new OneMoreValueHolder("yyy")});
    tested.setListOfOneMoreValueHolders(
        Arrays.asList(new OneMoreValueHolder("aaa"), new OneMoreValueHolder("bbb")));

    assertEquals(YAML, mapper.write(tested));
    assertEquals(YAML, mapper.write(mapper.read(mapper.write(tested))));
    assertEquals("zzz", mapper.read(mapper.write(tested)).getValue().getValue());
    assertEquals("bla-bla", mapper.read(mapper.write(tested)).getOneMoreValueHolder().getValue());
  }
}
