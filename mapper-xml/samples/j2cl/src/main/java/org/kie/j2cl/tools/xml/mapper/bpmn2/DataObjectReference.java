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

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;

public class DataObjectReference {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String dataObjectRef;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataObjectReference)) {
            return false;
        }
        DataObjectReference that = (DataObjectReference) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDataObjectRef(), that.getDataObjectRef());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDataObjectRef());
    }

    public String getId() {
        return id;
    }

    public String getDataObjectRef() {
        return dataObjectRef;
    }

    public void setDataObjectRef(String dataObjectRef) {
        this.dataObjectRef = dataObjectRef;
    }

    public void setDataObjectRef(DataObject dataObjectRef) {
        this.dataObjectRef = dataObjectRef.getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataObjectReference{" +
                "id='" + id + '\'' +
                ", dataObjectRef='" + dataObjectRef + '\'' +
                '}';
    }
}
