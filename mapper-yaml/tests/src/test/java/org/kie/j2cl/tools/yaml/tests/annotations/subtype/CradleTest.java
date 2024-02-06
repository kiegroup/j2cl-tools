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

package org.kie.j2cl.tools.yaml.tests.annotations.subtype;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(CradleTest.class)
public class CradleTest extends AbstractYamlTest<Cradle> {

  private static final Cradle_YamlMapperImpl mapper = Cradle_YamlMapperImpl.INSTANCE;

  public CradleTest() {
    super(mapper);
  }

  private final String YAML = "animal:" + "\n" + "  _type: cat" + "\n" + "  isCat: true";

  @Test
  public void test() throws IOException {
    Cradle cradle = new Cradle();
    Cat cat = new Cat();
    cat.setIsCat(true);
    cradle.setAnimal(cat);

    assertEquals(YAML, mapper.write(cradle));

    Cradle cradle1 = mapper.read(mapper.write(cradle));
    assertEquals(cradle, cradle1);
    assertEquals(mapper.write(cradle), mapper.write(cradle1));
  }
}
