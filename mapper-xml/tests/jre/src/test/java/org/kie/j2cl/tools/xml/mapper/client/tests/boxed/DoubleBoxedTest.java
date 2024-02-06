/*
 * Copyright © 2020 Treblereel
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
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 4/22/20 */
@J2clTestInput(DoubleBoxedTest.class)
public class DoubleBoxedTest {

  private static final String XML_EMPTY = "<?xml version='1.0' encoding='UTF-8'?><DoubleType/>";
  private static final String XML_17222 =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value>17222.02</value></DoubleType>";
  private static final String XML__17222 =
      "<?xml version='1.0' encoding='UTF-8'?><DoubleType><value>-17222.02</value></DoubleType>";

  private DoubleBoxedTest_DoubleType_XMLMapperImpl mapper =
      DoubleBoxedTest_DoubleType_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    DoubleType test = new DoubleType();
    assertEquals(XML_EMPTY, mapper.write(test));
    test.setValue(17222.02);
    assertEquals(new Double(17222.02), mapper.read(mapper.write(test)).value);
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setValue(-17222.02);
    assertEquals(new Double(-17222.02), mapper.read(mapper.write(test)).value);
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertNull(mapper.read(XML_EMPTY).getValue());
    assertEquals(new Double(17222.02), mapper.read(XML_17222).getValue(), 0.0);
    assertEquals(new Double(-17222.02), mapper.read(XML__17222).getValue(), 0.0);
  }

  @XMLMapper
  public static class DoubleType {

    private Double value;

    public Double getValue() {
      return value;
    }

    public void setValue(Double value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof DoubleType)) {
        return false;
      }
      DoubleType that = (DoubleType) o;
      return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }
  }
}
