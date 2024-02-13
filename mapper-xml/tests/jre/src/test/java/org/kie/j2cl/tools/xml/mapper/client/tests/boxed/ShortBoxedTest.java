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
package org.kie.j2cl.tools.xml.mapper.client.tests.boxed;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@J2clTestInput(ShortBoxedTest.class)
public class ShortBoxedTest {

  private static final String XML = "<?xml version='1.0' encoding='UTF-8'?><ShortType/>";
  private static final String XML_0 =
      "<?xml version='1.0' encoding='UTF-8'?><ShortType><check>0</check></ShortType>";
  private static final String XML_17222 =
      "<?xml version='1.0' encoding='UTF-8'?><ShortType><check>17222</check></ShortType>";
  private static final String XML__17222 =
      "<?xml version='1.0' encoding='UTF-8'?><ShortType><check>-17222</check></ShortType>";

  private ShortBoxedTest_ShortType_XMLMapperImpl mapper =
      ShortBoxedTest_ShortType_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    ShortType test = new ShortType();
    assertEquals(XML, mapper.write(test));
    test.setCheck((short) 17222);
    assertEquals(XML_17222, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck((short) -17222);
    assertEquals(XML__17222, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(Short.valueOf((short) 0), mapper.read(XML_0).getCheck());
    assertEquals(new Short((short) 17222), mapper.read(XML_17222).getCheck());
    assertEquals(new Short((short) -17222), mapper.read(XML__17222).getCheck());
  }

  @XMLMapper
  public static class ShortType {

    private Short check;

    public Short getCheck() {
      return check;
    }

    public void setCheck(Short check) {
      this.check = check;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ShortType)) {
        return false;
      }
      ShortType shortType = (ShortType) o;
      return Objects.equals(getCheck(), shortType.getCheck());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getCheck());
    }
  }
}
