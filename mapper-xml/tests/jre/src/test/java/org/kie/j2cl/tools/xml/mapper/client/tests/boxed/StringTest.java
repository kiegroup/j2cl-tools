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
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.StringBean;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.StringBean_XMLMapperImpl;

/** @author Dmitrii Tikhomirov Created by treblereel 3/26/20 */
@J2clTestInput(StringTest.class)
public class StringTest {

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(
        "XML",
        StringBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><StringBean><val>XML</val></StringBean>")
            .getVal());
    assertNull(
        StringBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><StringBean><val></val></StringBean>")
            .getVal());
  }

  @Test
  public void testSerializeValue() throws XMLStreamException {
    StringBean_XMLMapperImpl mapper = StringBean_XMLMapperImpl.INSTANCE;

    StringBean test = new StringBean();
    assertEquals("<?xml version='1.0' encoding='UTF-8'?><StringBean/>", mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setVal("XML");
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><StringBean><val>XML</val></StringBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
