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

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 6/21/21 */
@XMLMapper
@XmlRootElement(name = "target")
public class Target {

  @XmlElementRefs({
    @XmlElementRef(name = "JarTask", type = JarTask.class, required = false),
    @XmlElementRef(name = "JavacTask", type = JavacTask.class, required = false),
  })
  private List<Task> tasks;

  @XmlElementRefs({
    @XmlElementRef(name = "JarTask2", type = JarTask.class, required = false),
    @XmlElementRef(name = "JavacTask2", type = JavacTask.class, required = false),
  })
  @XmlElementWrapper(name = "the_wrapper")
  private List<Task> tasksXmlElementWrapper;

  @XmlElementRefs({
    @XmlElementRef(name = "task_JarTask", type = JarTask.class, required = false),
    @XmlElementRef(name = "task_JavacTask", type = JavacTask.class, required = false),
  })
  private Task task;

  @XmlElementWrapper
  @XmlElementRefs({
    @XmlElementRef(name = "_JarTask", type = JarTask.class, required = false),
    @XmlElementRef(name = "_JavacTask", type = JavacTask.class, required = false),
  })
  private Task task2;

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public Task getTask2() {
    return task2;
  }

  public void setTask2(Task task2) {
    this.task2 = task2;
  }

  public List<Task> getTasksXmlElementWrapper() {
    return tasksXmlElementWrapper;
  }

  public void setTasksXmlElementWrapper(List<Task> tasksXmlElementWrapper) {
    this.tasksXmlElementWrapper = tasksXmlElementWrapper;
  }
}
