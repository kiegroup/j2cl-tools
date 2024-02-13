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
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.UUID;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(UUIDTest.class)
public class UUIDTest {

  private static String uuid = "550e8400-e29b-41d4-a716-446655440000";
  UUIDBean_XMLMapperImpl mapper = UUIDBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(
        UUID.fromString(uuid),
        mapper
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><UUIDBean><val>"
                    + uuid
                    + "</val></UUIDBean>")
            .getVal());
    assertNull(
        mapper.read("<?xml version='1.0' encoding='UTF-8'?><UUIDBean><val/></UUIDBean>").getVal());
  }

  @Test
  public void testSerializeValue() throws XMLStreamException {
    UUIDBean test = new UUIDBean();
    assertEquals("<?xml version='1.0' encoding='UTF-8'?><UUIDBean/>", mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
    test.setVal(UUID.fromString(uuid));
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><UUIDBean><val>" + uuid + "</val></UUIDBean>",
        mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
