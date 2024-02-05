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
package org.kie.j2cl.tools.xml.mapper.bpmn.di;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BPMNPlane {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String bpmnElement;

    private Set<BPMNShape> shapes = new LinkedHashSet<>();

    public Set<BPMNShape> getShapes() {
        return shapes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBpmnElement() {
        return bpmnElement;
    }

    public void setBpmnElement(String bpmnElement) {
        this.bpmnElement = bpmnElement;
    }

    public void setShapes(Set<BPMNShape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BPMNPlane)) {
            return false;
        }
        BPMNPlane bpmnPlane = (BPMNPlane) o;
        return Objects.equals(getId(), bpmnPlane.getId()) &&
                Objects.equals(getBpmnElement(), bpmnPlane.getBpmnElement()) &&
                Objects.equals(getShapes(), bpmnPlane.getShapes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBpmnElement(), getShapes());
    }

    @Override
    public String toString() {
        StringBuilder shapesToString = new StringBuilder();
        if (shapes != null) {
            shapes.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> shapesToString.append(elm));
        }


        return "BPMNPlane{" +
                "id='" + id + '\'' +
                ", bpmnElement='" + bpmnElement + '\'' +
                "</br> shapes=" + shapesToString +
                '}';
    }
}
