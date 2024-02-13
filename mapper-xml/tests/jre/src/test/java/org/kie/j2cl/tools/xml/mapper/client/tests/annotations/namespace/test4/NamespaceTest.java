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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test4;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Assert;
import org.junit.Test;

@J2clTestInput(NamespaceTest.class)
public class NamespaceTest {
  NSTest_XMLMapperImpl mapper = NSTest_XMLMapperImpl.INSTANCE;

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><NSTest xmlns=\"http://www.omg.org/bpmn20\" xmlns:bpmn40=\"http://www.omg.org/bpmn40\"><value>ZZZ</value><value2 xmlns=\"http://www.omg.org/bpmn30\" value3=\"XXX3\" bpmn40:value4=\"XXX4\"><value2 xmlns=\"http://www.omg.org/bpmn30\">XXX2</value2><bpmn40:value5>XXX5</bpmn40:value5><value6 xmlns=\"http://www.omg.org/bpmn20\">XXX6</value6></value2></NSTest>";

  @Test
  public void test() throws XMLStreamException {
    NSTest test1 = new NSTest();
    test1.setValue("ZZZ");

    NSTest2 nsTest2 = new NSTest2();
    nsTest2.setValue2("XXX2");
    nsTest2.setValue3("XXX3");
    nsTest2.setValue4("XXX4");
    nsTest2.setValue5("XXX5");
    nsTest2.setValue6("XXX6");
    test1.setValue2(nsTest2);

    String xml = mapper.write(test1);

    // System.out.println("NamespaceTest 4 " + xml);
    assertEquals(XML, xml);
    NSTest tested = mapper.read(xml);
    NSTest2 tested1 = tested.getValue2();

    Assert.assertEquals(test1, mapper.read(xml));
  }
}
