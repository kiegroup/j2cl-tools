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

public class DataObject {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;
    @XmlAttribute
    private String itemSubjectRef;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataObject)) {
            return false;
        }
        DataObject that = (DataObject) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getItemSubjectRef(), that.getItemSubjectRef());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getItemSubjectRef());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemSubjectRef() {
        return itemSubjectRef;
    }

    public void setItemSubjectRef(String itemSubjectRef) {
        this.itemSubjectRef = itemSubjectRef;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", itemSubjectRef='" + itemSubjectRef + '\'' +
                '}';
    }
}
