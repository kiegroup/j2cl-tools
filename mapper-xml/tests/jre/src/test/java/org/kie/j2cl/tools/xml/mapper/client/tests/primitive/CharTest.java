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
package org.kie.j2cl.tools.xml.mapper.client.tests.primitive;

import static org.junit.Assert.assertEquals;

import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 4/22/20 */
// @J2clTestInput(CharTest.class) failed in htmlunit, works in browser
public class CharTest {
  private static final String XML_EMPTY =
      "<?xml version='1.0' encoding='UTF-8'?><CharType><value/></CharType>";
  private static final String XML_0 =
      "<?xml version='1.0' encoding='UTF-8'?><CharType><value></value></CharType>";
  private static final String XML_C =
      "<?xml version='1.0' encoding='UTF-8'?><CharType><value>c</value></CharType>";

  private CharTest_CharType_XMLMapperImpl mapper = CharTest_CharType_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    CharType test = new CharType();
    assertEquals(XML_0, mapper.write(test));
    test.setValue('c');
    assertEquals(XML_C, mapper.write(test));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals('\u0000', mapper.read(XML_0).getValue());
    assertEquals('c', mapper.read(XML_C).getValue());
    assertEquals('\u0000', mapper.read(XML_EMPTY).getValue());
  }

  @XMLMapper
  public static class CharType {

    private char value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof CharType)) {
        return false;
      }
      CharType intType = (CharType) o;
      return getValue() == intType.getValue();
    }

    public char getValue() {
      return value;
    }

    public void setValue(char value) {
      this.value = value;
    }
  }
}
