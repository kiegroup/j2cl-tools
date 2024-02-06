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
package org.kie.j2cl.tools.xml.mapper.client.bpmn2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;

import org.kie.j2cl.tools.xml.mapper.client.drools.MetaData;

public class SubProcess {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private boolean triggeredByEvent;

    private List<MetaData> extensionElements = new ArrayList<>();

    private List<DataObjectReference> dataObjectReference = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProcess)) {
            return false;
        }
        SubProcess that = (SubProcess) o;
        return isTriggeredByEvent() == that.isTriggeredByEvent() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getExtensionElements(), that.getExtensionElements()) &&
                Objects.equals(getDataObjectReference(), that.getDataObjectReference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isTriggeredByEvent(), getExtensionElements(), getDataObjectReference());
    }

    @Override
    public String toString() {
        StringBuilder extensionElementsToString = new StringBuilder();
        if (extensionElements != null) {
            extensionElements.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> extensionElementsToString.append(elm));
        }

        StringBuilder dataObjectReferenceToString = new StringBuilder();
        if (dataObjectReference != null) {
            dataObjectReference.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> dataObjectReferenceToString.append(elm));
        }


        return "SubProcess{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", triggeredByEvent=" + triggeredByEvent +
                "</br> extensionElements=" + extensionElementsToString +
                "</br> dataObjectReference=" + dataObjectReferenceToString +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isTriggeredByEvent() {
        return triggeredByEvent;
    }

    public List<MetaData> getExtensionElements() {
        return extensionElements;
    }

    public void setExtensionElements(List<MetaData> extensionElements) {
        this.extensionElements = extensionElements;
    }

    public List<DataObjectReference> getDataObjectReference() {
        return dataObjectReference;
    }

    public void setDataObjectReference(List<DataObjectReference> dataObjectReference) {
        this.dataObjectReference = dataObjectReference;
    }

    public void setTriggeredByEvent(boolean triggeredByEvent) {
        this.triggeredByEvent = triggeredByEvent;
    }

    public void setName(String name) {
        this.name = name;
    }
}
