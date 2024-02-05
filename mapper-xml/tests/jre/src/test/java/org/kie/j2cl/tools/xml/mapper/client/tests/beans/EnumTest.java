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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.DefaultXMLDeserializationContext;

@J2clTestInput(EnumTest.class)
public class EnumTest {
  EnumBean_XMLMapperImpl mapper = EnumBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(
        EnumBean.Enums.ONE,
        mapper
            .read("<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>1</val></EnumBean>")
            .getVal());
    assertEquals(
        null,
        mapper
            .INSTANCE
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>UNKNOWN</val></EnumBean>",
                DefaultXMLDeserializationContext.builder()
                    .readUnknownEnumValuesAsNull(true)
                    .build())
            .getVal());
  }

  @Test
  public void testSerializeValue() throws XMLStreamException {
    EnumBean test = new EnumBean();
    test.setVal(EnumBean.Enums.ONE);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>1</val></EnumBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));

    test.setVal(EnumBean.Enums.TWO);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>TWO</val></EnumBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));

    test.setVal(EnumBean.Enums.THREE);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><EnumBean><val>_three</val></EnumBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
