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
package org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn2;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** @author Dmitrii Tikhomirov Created by treblereel 4/6/20 */
@XmlRootElement(name = "process", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class Process {

  @XmlAttribute private String id;

  @XmlAttribute private String name;

  @XmlAttribute(name = "isExecutable")
  private boolean executable;

  @XmlAttribute(name = "packageName", namespace = "http://www.jboss.org/drools")
  private String packageName;

  @XmlAttribute(name = "version", namespace = "http://www.jboss.org/drools")
  private double version;

  @XmlAttribute(name = "adHoc", namespace = "http://www.jboss.org/drools")
  private boolean adHoc;

  @XmlElementRefs({
    @XmlElementRef(name = "userTask", type = UserTask.class),
    // @XmlElementRef(name = "bpmn2:scriptTask", type = ScriptTask.class)
  })
  private List<BPMNViewDefinition> definitionList;

  private List<SubProcess> subProcesses = new ArrayList<>();

  private List<DataObject> dataObjects = new ArrayList<>();

  private List<DataObjectReference> dataObjectReferences = new ArrayList<>();

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        isExecutable(),
        getPackageName(),
        getVersion(),
        isAdHoc(),
        getSubProcesses(),
        getDataObjects(),
        getDataObjectReferences());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Process)) {
      return false;
    }
    Process process = (Process) o;
    return isExecutable() == process.isExecutable()
        && isAdHoc() == process.isAdHoc()
        && Objects.equals(getId(), process.getId())
        && Objects.equals(getName(), process.getName())
        && Objects.equals(getPackageName(), process.getPackageName())
        && Objects.equals(getVersion(), process.getVersion())
        && Objects.equals(getSubProcesses(), process.getSubProcesses())
        && Objects.equals(getDataObjects(), process.getDataObjects())
        && Objects.equals(getDataObjectReferences(), process.getDataObjectReferences());
  }

  @Override
  public String toString() {
    return "Process{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", executable="
        + executable
        + ", packageName='"
        + packageName
        + '\''
        + ", version='"
        + version
        + '\''
        + ", adHoc="
        + adHoc
        + ", subProcesses="
        + subProcesses
        + ", dataObjects="
        + dataObjects
        + ", dataObjectReferences="
        + dataObjectReferences
        + '}';
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isExecutable() {
    return executable;
  }

  public void setExecutable(boolean executable) {
    this.executable = executable;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public double getVersion() {
    return version;
  }

  public boolean isAdHoc() {
    return adHoc;
  }

  public List<SubProcess> getSubProcesses() {
    return subProcesses;
  }

  public void setSubProcesses(List<SubProcess> subProcesses) {
    this.subProcesses = subProcesses;
  }

  public List<DataObject> getDataObjects() {
    return dataObjects;
  }

  public void setDataObjects(List<DataObject> dataObjects) {
    this.dataObjects = dataObjects;
  }

  public List<DataObjectReference> getDataObjectReferences() {
    return dataObjectReferences;
  }

  public void setDataObjectReferences(List<DataObjectReference> dataObjectReferences) {
    this.dataObjectReferences = dataObjectReferences;
  }

  public void setAdHoc(boolean adHoc) {
    this.adHoc = adHoc;
  }

  public void setVersion(double version) {
    this.version = version;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<BPMNViewDefinition> getDefinitionList() {
    return definitionList;
  }

  public void setDefinitionList(List<BPMNViewDefinition> definitionList) {
    this.definitionList = definitionList;
  }
}
