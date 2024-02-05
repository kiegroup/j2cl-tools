/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn2;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.drools.MetaData;

@XmlRootElement(name = "userTask", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class UserTask implements BPMNViewDefinition {

  @XmlAttribute private String id = "1111111111111112222222222";

  @XmlElementRefs({
    @XmlElementRef(name = "dataInput", type = DataInput.class),
    @XmlElementRef(name = "inputSet", type = InputSet.class),
  })
  @XmlElementWrapper(
      name = "ioSpecification",
      namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
  private List<Data> ioSpecification;

  @XmlElementWrapper(
      name = "extensionElements",
      namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
  private List<MetaData> extensionElements;

  public UserTask() {}

  public List<Data> getIoSpecification() {
    return ioSpecification;
  }

  public void setIoSpecification(List<Data> ioSpecification) {
    this.ioSpecification = ioSpecification;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<MetaData> getExtensionElements() {
    return extensionElements;
  }

  public void setExtensionElements(List<MetaData> extensionElements) {
    this.extensionElements = extensionElements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserTask)) return false;

    UserTask userTask = (UserTask) o;

    if (getId() != null ? !getId().equals(userTask.getId()) : userTask.getId() != null)
      return false;
    if (getIoSpecification() != null
        ? !getIoSpecification().equals(userTask.getIoSpecification())
        : userTask.getIoSpecification() != null) return false;
    return getExtensionElements() != null
        ? getExtensionElements().equals(userTask.getExtensionElements())
        : userTask.getExtensionElements() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getIoSpecification() != null ? getIoSpecification().hashCode() : 0);
    result = 31 * result + (getExtensionElements() != null ? getExtensionElements().hashCode() : 0);
    return result;
  }
}
