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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.ci.Tutorial;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.ci.Tutorial_XMLMapperImpl;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.cl.Name;

/** @author Dmitrii Tikhomirov Created by treblereel 4/28/20 */
@J2clTestInput(TutorialTest.class)
public class TutorialTest {
  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><_tutorial xmlns=\"http://ns\" xmlns:ci=\"http://www.ci\" xmlns:cl=\"http://www.cl\"><id xmlns=\"http://ns\">0</id><cl:names><name>NAME</name></cl:names><cl:names><name>NAME2</name></cl:names><wrapper><cl:types><name>NAME</name></cl:types><cl:types><name>NAME2</name></cl:types></wrapper><cl:arrays><name>F1</name></cl:arrays><cl:arrays><name>F2</name></cl:arrays></_tutorial>";
  private static final Tutorial_XMLMapperImpl mapper = Tutorial_XMLMapperImpl.INSTANCE;

  @Test
  public void test() throws XMLStreamException {
    Tutorial tutorial = new Tutorial();
    Name name1 = new Name("NAME");
    Name name2 = new Name("NAME2");
    List<Name> names = new ArrayList<>();
    tutorial.setNames(names);
    tutorial.setTypes(names);
    names.add(name1);
    names.add(name2);

    Name[] arr = new Name[2];
    arr[0] = new Name("F1");
    arr[1] = new Name("F2");
    tutorial.setArrays(arr);

    String xml = mapper.write(tutorial);
    assertEquals(XML, xml);
    assertEquals(tutorial, mapper.read(xml));
    assertEquals(xml, mapper.write(mapper.read(xml)));
  }
}
