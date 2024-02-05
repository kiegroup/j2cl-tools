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
package org.kie.j2cl.tools.xml.mapper.bpmn2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Process {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "isExecutable")
    private boolean executable;

    @XmlAttribute(name = "drools:packageName")
    private String packageName;

    @XmlAttribute(name = "drools:version")
    private String version;

    @XmlAttribute(name = "drools:adHoc")
    private boolean adHoc;

    private List<SubProcess> subProcesses = new ArrayList<>();

    private List<DataObject> dataObjects = new ArrayList<>();

    private List<DataObjectReference> dataObjectReferences = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isExecutable(), getPackageName(), getVersion(), isAdHoc(), getSubProcesses(), getDataObjects(), getDataObjectReferences());
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
        return isExecutable() == process.isExecutable() &&
                isAdHoc() == process.isAdHoc() &&
                Objects.equals(getId(), process.getId()) &&
                Objects.equals(getName(), process.getName()) &&
                Objects.equals(getPackageName(), process.getPackageName()) &&
                Objects.equals(getVersion(), process.getVersion()) &&
                Objects.equals(getSubProcesses(), process.getSubProcesses()) &&
                Objects.equals(getDataObjects(), process.getDataObjects()) &&
                Objects.equals(getDataObjectReferences(), process.getDataObjectReferences());
    }

    @Override
    public String toString() {
        StringBuilder subProcessesToString = new StringBuilder();
        StringBuilder dataObjectsToString = new StringBuilder();
        StringBuilder dataObjectReferencesToString = new StringBuilder();
        if (subProcesses != null) {
            subProcesses.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> subProcessesToString.append(elm));
        }

        if (dataObjectsToString != null) {
            dataObjects.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> dataObjectsToString.append(elm));
        }

        if (dataObjectReferencesToString != null) {
            dataObjectReferences.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> dataObjectReferencesToString.append(elm));
        }

        return "Process{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", executable=" + executable +
                ", packageName='" + packageName + '\'' +
                ", version='" + version + '\'' +
                ", adHoc=" + adHoc +
                "</br> subProcesses=" + subProcessesToString +
                "</br> dataObjects :" + dataObjectsToString +
                "</br> dataObjectReferences :" + dataObjectReferencesToString +
                '}';
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public boolean isAdHoc() {
        return adHoc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
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

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdHoc(boolean adHoc) {
        this.adHoc = adHoc;
    }
}
