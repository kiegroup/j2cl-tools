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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test3;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Assert;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 10/4/20 */
@J2clTestInput(NamespaceTest.class)
public class NamespaceTest {
  NSTest_XMLMapperImpl mapper = NSTest_XMLMapperImpl.INSTANCE;

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><NSTest xmlns=\"http://www.omg.org/bpmn20\"><value>ZZZ</value><value2><value2>XXX</value2></value2></NSTest>";

  @Test
  public void test() throws XMLStreamException {
    NSTest test1 = new NSTest();
    test1.setValue("ZZZ");

    NSTest2 nsTest2 = new NSTest2();
    nsTest2.setValue2("XXX");
    test1.setValue2(nsTest2);

    String xml = mapper.write(test1);

    // System.out.println("NamespaceTest 2 " + xml);
    assertEquals(XML, xml);
    Assert.assertEquals(test1, mapper.read(xml));
  }
}
