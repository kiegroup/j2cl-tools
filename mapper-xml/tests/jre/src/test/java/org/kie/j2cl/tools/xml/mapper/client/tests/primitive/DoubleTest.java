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

@J2clTestInput(DoubleTest.class)
public class DoubleTest {

  private static final String XML_EMPTY =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value/></DoubleType>";
  private static final String XML_TAG_EMPTY =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value></value></DoubleType>";
  private static final String XML_0 =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value>0.0</value></DoubleType>";
  private static final String XML_17222 =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value>17222.0</value></DoubleType>";
  private static final String XML__17222 =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value>-17222.0</value></DoubleType>";

  private DoubleTest_DoubleType_XMLMapperImpl mapper = DoubleTest_DoubleType_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    DoubleType test = new DoubleType();
    assertEquals(new Double(0), mapper.read(mapper.write(test)).value, 0.1);
    test.setValue(17222);
    assertEquals(17222.02, mapper.read(mapper.write(test)).value, 0.1);
    test.setValue(-17222);
    assertEquals(-17222.02, mapper.read(mapper.write(test)).value, 0.1);
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(0.0, mapper.read(XML_EMPTY).getValue(), 0.0);
    assertEquals(0.0, mapper.read(XML_0).getValue(), 0.0);
    assertEquals(0.0, mapper.read(XML_TAG_EMPTY).getValue(), 0.0);
    assertEquals(17222.0, mapper.read(XML_17222).getValue(), 0.0);
    assertEquals(-17222.0, mapper.read(XML__17222).getValue(), 0.0);
  }

  @XMLMapper
  public static class DoubleType {

    private double value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof DoubleType)) {
        return false;
      }
      DoubleType type = (DoubleType) o;
      return getValue() == type.getValue();
    }

    public double getValue() {
      return value;
    }

    public void setValue(double value) {
      this.value = value;
    }
  }
}
