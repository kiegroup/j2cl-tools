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
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.DefaultXMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.DefaultXMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.date.DateBean;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.date.DateBean_XMLMapperImpl;

@J2clTestInput(DateTest.class)
public class DateTest {

  private static final String XML_TIMESTAMP =
      "<?xml version='1.0' encoding='UTF-8'?><DateBean val2=\"1377543971773\"><val>1377543971773</val></DateBean>";

  private static final String XML_ISO =
      "<?xml version='1.0' encoding='UTF-8'?><DateBean val2=\"2013-08-26T19:06:11.773Z\"><val>2013-08-26T19:06:11.773Z</val></DateBean>";

  private static final String XML_ISO_NULL =
      "<?xml version='1.0' encoding='UTF-8'?><DateBean val2=\"\"><val/></DateBean>";

  private final DateBean_XMLMapperImpl mapper = DateBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testDatesAsTimestampsValue() throws XMLStreamException {
    DateBean dateBean = new DateBean();
    dateBean.setVal(new Date(1377543971773l));
    dateBean.setVal2(new Date(1377543971773l));

    assertEquals(XML_TIMESTAMP, mapper.write(dateBean));
    assertEquals(dateBean, mapper.read(mapper.write(dateBean)));
  }

  @Test
  public void testDatesAsISO8601Value() throws XMLStreamException {
    XMLDeserializationContext deserializationContext =
        DefaultXMLDeserializationContext.builder().readDateAsTimestamp(false).build();

    XMLSerializationContext serializationContext =
        DefaultXMLSerializationContext.builder().writeDatesAsTimestamps(false).build();

    assertNull(
        mapper
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><DateBean><val/></DateBean>",
                deserializationContext)
            .getVal());

    DateBean dateBean = new DateBean();
    dateBean.setVal(new Date(1377543971773l));
    dateBean.setVal2(new Date(1377543971773l));

    assertEquals(XML_ISO, mapper.write(dateBean, serializationContext));
    assertEquals(
        dateBean,
        mapper.read(mapper.write(dateBean, serializationContext), deserializationContext));
  }

  @Test
  public void testDatesAsISO8601NullValue() throws XMLStreamException {
    XMLDeserializationContext deserializationContext =
        DefaultXMLDeserializationContext.builder().readDateAsTimestamp(false).build();

    XMLSerializationContext serializationContext =
        DefaultXMLSerializationContext.builder()
            .serializeNulls(true)
            .writeDatesAsTimestamps(false)
            .build();

    DateBean dateBean = new DateBean();

    assertEquals(XML_ISO_NULL, mapper.write(dateBean, serializationContext));
    assertEquals(
        dateBean,
        mapper.read(mapper.write(dateBean, serializationContext), deserializationContext));
  }
}
