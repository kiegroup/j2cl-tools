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

package org.kie.j2cl.tools.xml.mapper.client.tests.generics;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 7/15/20 */
@J2clTestInput(SampleGenericTypeTest.class)
public class SampleGenericTypeTest {
  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><SampleGenericType><field3>setField3</field3><field1>setField1</field1><field2>1</field2></SampleGenericType>";
  SampleGenericType_XMLMapperImpl mapper = SampleGenericType_XMLMapperImpl.INSTANCE;

  @Test
  public void test() throws XMLStreamException {
    SampleGenericType tested = new SampleGenericType();
    tested.setField1("setField1");
    tested.setField2(1);
    tested.setField3("setField3");

    String result = mapper.write(tested);
    assertEquals(XML, result);
    assertEquals(tested, mapper.read(result));
    assertEquals(XML, mapper.write(mapper.read(result)));
  }
}
