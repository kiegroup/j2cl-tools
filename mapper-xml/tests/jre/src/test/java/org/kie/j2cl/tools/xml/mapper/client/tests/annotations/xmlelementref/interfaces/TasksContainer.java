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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmlelementref.interfaces;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
@XmlRootElement(name = "tasks")
public class TasksContainer {

  @XmlElement(name = "Task1", type = Task.class)
  private ITask task1;

  @XmlElementRef(name = "Task2", type = Task.class)
  private ITask task2Ref;

  @XmlElement(name = "TaskList", type = Task.class)
  private List<ITask> taskList;

  @XmlElementRef(name = "TaskListRef", type = Task.class)
  private List<ITask> taskListRef;

  @XmlElement(name = "TaskArray", type = Task.class)
  private ITask[] taskArray;

  @XmlElementRef(name = "TaskArrayRef", type = Task.class)
  private ITask[] taskArrayRef;

  public ITask getTask1() {
    return task1;
  }

  public void setTask1(ITask task1) {
    this.task1 = task1;
  }

  public ITask getTask2Ref() {
    return task2Ref;
  }

  public void setTask2Ref(ITask task2) {
    this.task2Ref = task2;
  }

  public List<ITask> getTaskList() {
    return taskList;
  }

  public void setTaskList(List<ITask> taskList) {
    this.taskList = taskList;
  }

  public List<ITask> getTaskListRef() {
    return taskListRef;
  }

  public void setTaskListRef(List<ITask> taskListRef) {
    this.taskListRef = taskListRef;
  }

  public ITask[] getTaskArray() {
    return taskArray;
  }

  public void setTaskArray(ITask[] taskArray) {
    this.taskArray = taskArray;
  }

  public ITask[] getTaskArrayRef() {
    return taskArrayRef;
  }

  public void setTaskArrayRef(ITask[] taskArrayRef) {
    this.taskArrayRef = taskArrayRef;
  }
}
