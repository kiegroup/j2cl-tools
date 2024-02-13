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
package org.kie.j2cl.tools.xml.mapper.client.tests.collections;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.StringMap;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.StringMap_XMLMapperImpl;

@J2clTestInput(StringMapTest.class)
public class StringMapTest {

  private final String xml =
      "<?xml version='1.0' encoding='UTF-8'?><StringMap><map><entry><key>key1</key><value>value1</value></entry><entry><key>key2</key><value>value2</value></entry><entry><key>key3</key><value>value3</value></entry></map><checkNewLine>one more line</checkNewLine></StringMap>";
  StringMap_XMLMapperImpl mapper = StringMap_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");

    StringMap test = new StringMap();
    test.setMap(map);
    test.setCheckNewLine("one more line");

    String result = mapper.write(test);

    assertEquals(xml, result);
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
