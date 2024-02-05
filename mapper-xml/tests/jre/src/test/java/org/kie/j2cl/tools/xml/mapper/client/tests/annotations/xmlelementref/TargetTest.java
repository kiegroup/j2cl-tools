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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlelementref;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 6/21/21 */
@J2clTestInput(TargetTest.class)
public class TargetTest {

  Target_XMLMapperImpl mapper = Target_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeAndDeserializeValue() throws XMLStreamException {
    final String XML =
        "<?xml version='1.0' encoding='UTF-8'?><target><JarTask><firstName>setFirstName</firstName></JarTask></target>";

    Target target = new Target();
    JarTask jarTask = new JarTask();
    jarTask.setFirstName("setFirstName");

    target.setTasks(new ArrayList<>());
    target.getTasks().add(jarTask);

    String result = mapper.write(target);
    assertEquals(XML, result);
    assertEquals(result, mapper.write(mapper.read(mapper.write(target))));
  }

  @Test
  public void testSerializeAndDeserializeValue1() throws XMLStreamException {
    final String XML =
        "<?xml version='1.0' encoding='UTF-8'?><target><JarTask><firstName>setFirstName</firstName></JarTask><JavacTask><lastName>setLastName</lastName></JavacTask><the_wrapper><JarTask2><firstName>setFirstName</firstName></JarTask2><JavacTask2><lastName>setLastName</lastName></JavacTask2></the_wrapper><task_JarTask><firstName>JarTask</firstName></task_JarTask><task2><_JarTask><firstName>JarTask</firstName></_JarTask></task2></target>";
    Target target = new Target();
    JarTask jarTask = new JarTask();
    jarTask.setFirstName("setFirstName");

    JavacTask javacTask = new JavacTask();
    javacTask.setLastName("setLastName");
    target.setTasks(new ArrayList<>());
    target.getTasks().add(jarTask);
    target.getTasks().add(javacTask);

    JarTask jarTask2 = new JarTask();
    jarTask2.setFirstName("JarTask");
    target.setTask(jarTask2);

    target.setTasksXmlElementWrapper(new ArrayList<>());

    target.getTasksXmlElementWrapper().add(jarTask);
    target.getTasksXmlElementWrapper().add(javacTask);
    target.setTask2(jarTask2);

    String result = mapper.write(target);
    assertEquals(XML, result);
    assertEquals(XML, mapper.write(mapper.read(mapper.write(target))));
  }

  @Test
  public void testSerializeAndDeserializeValue2() throws XMLStreamException {
    final String XML =
        "<?xml version='1.0' encoding='UTF-8'?><target><the_wrapper><JarTask2><firstName>setFirstName</firstName></JarTask2><JavacTask2><lastName>setLastName</lastName></JavacTask2></the_wrapper></target>";
    Target target = new Target();

    JarTask jarTask = new JarTask();
    jarTask.setFirstName("setFirstName");

    JavacTask javacTask = new JavacTask();
    javacTask.setLastName("setLastName");

    target.setTasksXmlElementWrapper(new ArrayList<>());
    target.getTasksXmlElementWrapper().add(jarTask);
    target.getTasksXmlElementWrapper().add(javacTask);

    String result = mapper.write(target);
    assertEquals(XML, result);
    assertEquals(result, mapper.write(mapper.read(mapper.write(target))));
  }

  @Test
  public void testSerializeAndDeserializeValue3() throws XMLStreamException {
    final String XML = "<?xml version='1.0' encoding='UTF-8'?><target><the_wrapper/></target>";

    Target target = new Target();
    target.setTasks(new ArrayList<>());
    target.setTasksXmlElementWrapper(new ArrayList<>());

    String result = mapper.write(target);
    assertEquals(XML, result);
  }
}
