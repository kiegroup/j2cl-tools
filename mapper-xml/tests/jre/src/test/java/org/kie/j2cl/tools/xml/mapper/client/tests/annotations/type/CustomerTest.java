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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.type;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(CustomerTest.class)
public class CustomerTest {

  Customer_XMLMapperImpl mapper = Customer_XMLMapperImpl.INSTANCE;

  private static final String xml =
      "<?xml version='1.0' encoding='UTF-8'?><Customer xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><id>1112</id><firstName>setFirstName</firstName><lastName>setLastName</lastName><notInPropOrder>setNotInPropOrder</notInPropOrder></Customer>";

  @Test
  public void testSerializeValue() throws XMLStreamException {
    Customer customer = new Customer();
    customer.setId(1112);
    customer.setFirstName("setFirstName");
    customer.setLastName("setLastName");
    customer.setNotInPropOrder("setNotInPropOrder");

    assertEquals(xml, mapper.write(customer));
    assertEquals(customer, mapper.read(mapper.write(customer)));
  }
}
