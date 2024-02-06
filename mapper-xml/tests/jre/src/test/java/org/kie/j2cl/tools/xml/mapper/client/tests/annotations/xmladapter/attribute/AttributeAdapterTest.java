/*
 * Copyright © 2021
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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.attribute;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.MyTestBeanTest;

@J2clTestInput(MyTestBeanTest.class)
public class AttributeAdapterTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><BeanWithAttribute value=\"test value\"/>";
  private static final String ATTRIBUTE_VALUE = "test value";

  BeanWithAttribute_XMLMapperImpl mapper = BeanWithAttribute_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    BeanWithAttribute test = new BeanWithAttribute();
    test.setValue(new AttributeObject(ATTRIBUTE_VALUE));
    assertEquals(XML, mapper.write(test));
  }

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    BeanWithAttribute test = mapper.read(XML);
    assertEquals(ATTRIBUTE_VALUE, test.getValue().getValue());
  }
}
