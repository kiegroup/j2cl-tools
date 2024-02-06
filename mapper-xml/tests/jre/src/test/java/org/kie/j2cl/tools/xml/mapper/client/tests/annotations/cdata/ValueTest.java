/*
 * Copyright © 2020 Treblereel
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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.cdata;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 9/8/20 */
@J2clTestInput(ValueTest.class)
public class ValueTest {

  private final Value_XMLMapperImpl mapper = Value_XMLMapperImpl.INSTANCE;

  @Test
  public void testValueTest() throws XMLStreamException {
    Value test = new Value();
    test.setId("_id");
    test.setValue("VALUE");

    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><Value xmlns=\"http://www.example.org/package\" id=\"_id\"><![CDATA[VALUE]]></Value>",
        mapper.write(test));

    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
