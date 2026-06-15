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

package org.kie.j2cl.tools.yaml.tests.annotations.setters;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import org.junit.Test;

@J2clTestInput(GetterSetterTest.class)
public class GetterSetterTest {

  private static final Tested_YamlMapperImpl mapper = Tested_YamlMapperImpl.INSTANCE;

  @Test
  public void test() throws IOException {
    Tested tested = new Tested("test");
    String yaml = mapper.write(tested);
    assertEquals(tested, mapper.read(yaml));
  }
}
