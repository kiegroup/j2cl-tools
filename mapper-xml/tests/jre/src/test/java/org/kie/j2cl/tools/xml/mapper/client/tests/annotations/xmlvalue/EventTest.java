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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlvalue;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(EventTest.class)
public class EventTest {

  private static final Event_XMLMapperImpl mapper = Event_XMLMapperImpl.INSTANCE;
  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><Event type=\"setType\" gender=\"setGender\">setDescription</Event>";

  @Test
  public void testSerializeValue() throws XMLStreamException {
    Event test = new Event();
    test.setType("setType");
    test.setGender("setGender");
    test.setDescription("setDescription");
    test.setDescription2("setDescription2");
    String result = mapper.write(test);
    assertEquals(XML, result);
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    Event test = new Event();
    test.setType("setType");
    test.setGender("setGender");
    test.setDescription("setDescription");
    test.setDescription2("setDescription2");

    Event result = mapper.read(XML);
    assertEquals(XML, mapper.write(result));
    assertEquals(test, result);
  }
}
