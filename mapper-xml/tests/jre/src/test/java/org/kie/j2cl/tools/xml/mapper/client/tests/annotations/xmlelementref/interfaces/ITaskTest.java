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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlelementref.interfaces;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(ITaskTest.class)
public class ITaskTest {

  TasksContainer_XMLMapperImpl mapper = TasksContainer_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeAndDeserializeValue() throws XMLStreamException {
    final String XML =
        "<?xml version='1.0' encoding='UTF-8'?>"
            + "<tasks>"
            + "<Task1><name>task1Name</name></Task1>"
            + "<task2Ref><name>task2Name</name></task2Ref>"
            + "<TaskList><name>task3ListName</name></TaskList>"
            + "<TaskList><name>task4ListName</name></TaskList>"
            + "<taskListRef><name>task5ListRefName</name></taskListRef>"
            + "<taskListRef><name>task6ListRefName</name></taskListRef>"
            + "<TaskArray><name>task7ArrayName</name></TaskArray>"
            + "<TaskArray><name>task8ArrayName</name></TaskArray>"
            + "<taskArrayRef><name>task8ArrayRefName</name></taskArrayRef>"
            + "<taskArrayRef><name>task9ArrayRefName</name></taskArrayRef>"
            + "</tasks>";
    TasksContainer container = new TasksContainer();
    container.setTask1(new Task("task1Name"));
    container.setTask2Ref(new Task("task2Name"));
    container.setTaskList(Arrays.asList(new Task("task3ListName"), new Task("task4ListName")));
    container.setTaskListRef(
        Arrays.asList(new Task("task5ListRefName"), new Task("task6ListRefName")));
    container.setTaskArray(new ITask[] {new Task("task7ArrayName"), new Task("task8ArrayName")});
    container.setTaskArrayRef(
        new ITask[] {new Task("task8ArrayRefName"), new Task("task9ArrayRefName")});

    String result = mapper.write(container);

    assertEquals(XML, result);
    assertEquals(result, mapper.write(mapper.read(mapper.write(container))));
  }
}
