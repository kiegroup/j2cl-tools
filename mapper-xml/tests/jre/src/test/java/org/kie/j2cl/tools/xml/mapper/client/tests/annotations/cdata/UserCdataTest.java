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
import java.util.UUID;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 4/4/20 */
@J2clTestInput(UserCdataTest.class)
public class UserCdataTest {

  User_XMLMapperImpl mapperEmployee = User_XMLMapperImpl.INSTANCE;

  @Test
  public void testUserCdataTest() throws XMLStreamException {
    User test = new User();
    test.setUsername("ANY");
    test.setUuid(UUID.fromString("bc8a6b10-f493-4aaf-bd1e-8c4710afa326"));
    test.setId("FIRST");
    test.setTime(1111);

    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><User xmlns=\"http://www.omg.org/bpmn20\" id=\"FIRST\" _uuid=\"bc8a6b10-f493-4aaf-bd1e-8c4710afa326\" time=\"1111\"><username xmlns=\"http://www.omg.org/bpmn20\"><![CDATA[ANY]]></username></User>",
        mapperEmployee.write(test));
    assertEquals(test, mapperEmployee.read(mapperEmployee.write(test)));
  }
}
