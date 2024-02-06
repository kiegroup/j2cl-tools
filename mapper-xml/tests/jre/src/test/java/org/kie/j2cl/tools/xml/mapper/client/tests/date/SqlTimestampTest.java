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
package org.kie.j2cl.tools.xml.mapper.client.tests.date;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.sql.Timestamp;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.date.TimestampBean_XMLMapperImpl;

@J2clTestInput(SqlTimestampTest.class)
public class SqlTimestampTest {

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(
        new Timestamp(0),
        TimestampBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><TimestampBean><val/></TimestampBean>")
            .getVal());
    assertEquals(
        new Timestamp(0),
        TimestampBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><TimestampBean><val/></TimestampBean>")
            .getVal());
    assertEquals(
        new Timestamp(1377543971773l),
        TimestampBean_XMLMapperImpl.INSTANCE
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><TimestampBean><val>1377543971773</val></TimestampBean>")
            .getVal());
  }
}
