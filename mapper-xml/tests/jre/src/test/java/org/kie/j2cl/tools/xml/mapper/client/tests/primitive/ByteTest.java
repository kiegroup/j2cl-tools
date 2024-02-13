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
package org.kie.j2cl.tools.xml.mapper.client.tests.primitive;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@J2clTestInput(ByteTest.class)
public class ByteTest {

  private static final String XML_0 =
      "<?xml version='1.0' encoding='UTF-8'?><ByteType><check>0</check></ByteType>";
  private static final String XML_123 =
      "<?xml version='1.0' encoding='UTF-8'?><ByteType><check>123</check></ByteType>";
  private static final String XML_22 =
      "<?xml version='1.0' encoding='UTF-8'?><ByteType><check>-22</check></ByteType>";

  private ByteTest_ByteType_XMLMapperImpl mapper = ByteTest_ByteType_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    ByteType test = new ByteType();
    assertEquals(XML_0, mapper.write(test));
    test.setCheck((byte) 123);
    assertEquals(XML_123, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck((byte) -22);
    assertEquals(XML_22, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(0, mapper.read(XML_0).getCheck());
    assertEquals(123, mapper.read(XML_123).getCheck());
    assertEquals(-22, mapper.read(XML_22).getCheck());
  }

  @XMLMapper
  public static class ByteType {

    private byte check;

    @Override
    public int hashCode() {
      return Objects.hash(getCheck());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ByteType)) {
        return false;
      }
      ByteType byteType = (ByteType) o;
      return getCheck() == byteType.getCheck();
    }

    public byte getCheck() {
      return check;
    }

    public void setCheck(byte check) {
      this.check = check;
    }
  }
}
