/*
 * Copyright © 2021 Treblereel
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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlaccesstype.field;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 7/13/21 */
@J2clTestInput(CustomerTest.class)
public class CustomerTest {

  private static final String xml =
      "<?xml version='1.0' encoding='UTF-8'?><Customer id=\"1112\"><firstName><![CDATA[setFirstName]]></firstName><lastName>setLastName</lastName><names>AAAA</names><names>BBB</names><names>CCCC</names></Customer>";
  private static final Customer_XMLMapperImpl mapper = Customer_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    Customer customer = new Customer();
    customer.id = 1112;
    customer.firstName = "setFirstName";
    customer.lastName = "setLastName";

    customer.names.add("AAAA");
    customer.names.add("BBB");
    customer.names.add("CCCC");

    assertEquals(xml, mapper.write(customer));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    Customer customer = new Customer();
    customer.id = 1112;
    customer.firstName = "setFirstName";
    customer.lastName = "setLastName";
    customer.names.add("AAAA");
    customer.names.add("BBB");
    customer.names.add("CCCC");

    assertEquals(customer, mapper.read(xml));
    assertEquals(xml, mapper.write(mapper.read(mapper.write(mapper.read(xml)))));
    assertEquals(customer, mapper.read(mapper.write(mapper.read(xml))));
  }
}
