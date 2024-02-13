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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.BooleanBean;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.BooleanBean_XMLMapperImpl;

@J2clTestInput(BooleanTest.class)
public class BooleanTest extends GWTTestCase {

  private static final String XML_TRUE =
      "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>true</check></BooleanBean>";
  private static final String XML_FALSE =
      "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>false</check></BooleanBean>";

  private BooleanBean_XMLMapperImpl mapper = BooleanBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    BooleanBean test = new BooleanBean();
    assertEquals(XML_FALSE, mapper.write(test));
    test.setCheck(true);
    assertEquals(XML_TRUE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck(false);
    assertEquals(XML_FALSE, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertTrue(mapper.read(XML_TRUE).getCheck());
    assertFalse(mapper.read(XML_FALSE).getCheck());
    BooleanBean test = new BooleanBean();
    test.setCheck(true);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>true</check></BooleanBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setCheck(false);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>false</check></BooleanBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }

  @Override
  public String getModuleName() {
    return "org.kie.j2cl.tools.xml.mapper.XMLTest";
  }
}
