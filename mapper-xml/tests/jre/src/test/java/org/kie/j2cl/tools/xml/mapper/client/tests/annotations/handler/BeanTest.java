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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(BeanTest.class)
public class BeanTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><Bean id=\"d80b09e6-9a00-11ea-bb37-0242ac130002\"><myBean>BeanTest+BeanTest2</myBean></Bean>";

  Bean_XMLMapperImpl mapper = Bean_XMLMapperImpl.INSTANCE;

  @Test
  public void test() throws XMLStreamException {
    Bean test = new Bean();
    test.setId(new Bean.Id("d80b09e6-9a00-11ea-bb37-0242ac130002"));
    MyBean bean = new MyBean();
    bean.setValue("BeanTest");
    bean.setValue2("BeanTest2");
    test.setMyBean(bean);

    assertEquals(XML, mapper.write(test));
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
