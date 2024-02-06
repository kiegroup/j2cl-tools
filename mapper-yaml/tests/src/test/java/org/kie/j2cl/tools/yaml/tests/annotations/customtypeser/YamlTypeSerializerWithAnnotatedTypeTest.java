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
import org.junit.Test;

@J2clTestInput(YamlTypeSerializerWithAnnotatedTypeTest.class)
public class YamlTypeSerializerWithAnnotatedTypeTest {

  private static final Tested_YamlMapperImpl mapper = Tested_YamlMapperImpl.INSTANCE;

  @Test
  public void test() {
    Tested tested = new Tested();
    assertEquals("", mapper.write(tested));
  }

  @Test
  public void test2() throws IOException {
    ValueHolder valueHolder = new ValueHolder("zzz");
    Tested tested = new Tested();
    tested.setValue(valueHolder);
    assertEquals("value: ZZZ", mapper.write(tested));
    assertEquals("zzz", mapper.read(mapper.write(tested)).getValue().getValue());
  }
}
