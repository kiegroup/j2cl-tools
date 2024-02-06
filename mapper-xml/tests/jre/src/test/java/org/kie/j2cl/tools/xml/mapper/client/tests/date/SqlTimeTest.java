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
import java.sql.Time;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.date.TimeBean_XMLMapperImpl;

@J2clTestInput(SqlTimeTest.class)
public class SqlTimeTest {

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertEquals(
        new Time(0),
        TimeBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><TimeBean><val/></TimeBean>")
            .getVal());
    assertEquals(
        new Time(0),
        TimeBean_XMLMapperImpl.INSTANCE
            .read("<?xml version='1.0' encoding='UTF-8'?><TimeBean><val/></TimeBean>")
            .getVal());
    assertEquals(
        new Time(1377543971773l),
        TimeBean_XMLMapperImpl.INSTANCE
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><TimeBean><val>1377543971773</val></TimeBean>")
            .getVal());
  }
}
