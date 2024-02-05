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
import java.math.BigDecimal;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 4/22/20 */
@J2clTestInput(BigDecimalTest.class)
public class BigDecimalTest {

  private static final String value =
      "15487846511321245665435132032454.1545815468465578451323888744";

  private static final String XML_EMPTY = "<?xml version='1.0' encoding='UTF-8'?><BigDecimalBean/>";
  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><BigDecimalBean><value>"
          + value
          + "</value></BigDecimalBean>";

  private BigDecimalTest_BigDecimalBean_XMLMapperImpl mapper =
      BigDecimalTest_BigDecimalBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    BigDecimalBean test = new BigDecimalBean();
    assertEquals(XML_EMPTY, mapper.write(test));
    test.setValue(new BigDecimal(value));
    assertEquals(XML, mapper.write(test));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    BigDecimalBean test = new BigDecimalBean();
    test.setValue(new BigDecimal(value));
    assertNull(mapper.read(XML_EMPTY).getValue());
    assertEquals(test, mapper.read(XML));
  }

  @XMLMapper
  public static class BigDecimalBean {

    private BigDecimal value;

    public BigDecimal getValue() {
      return value;
    }

    public void setValue(BigDecimal value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof BigDecimalBean)) {
        return false;
      }
      BigDecimalBean intType = (BigDecimalBean) o;
      return Objects.equals(getValue(), intType.getValue());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }
  }
}
