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

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;

import org.kie.j2cl.tools.xml.mapper.bpmn.dc.Bounds;

public class BPMNShape {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String bpmnElement;

    @XmlAttribute(name = "isExpanded")
    private boolean expanded;

    private Bounds bounds;

    public boolean isExpanded() {
        return expanded;
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

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BPMNShape)) {
            return false;
        }
        BPMNShape bpmnShape = (BPMNShape) o;
        return isExpanded() == bpmnShape.isExpanded() &&
                Objects.equals(getId(), bpmnShape.getId()) &&
                Objects.equals(getBpmnElement(), bpmnShape.getBpmnElement()) &&
                Objects.equals(getBounds(), bpmnShape.getBounds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBpmnElement(), isExpanded(), getBounds());
    }

    @Override
    public String toString() {
        return "BPMNShape{" +
                "id='" + id + '\'' +
                ", bpmnElement='" + bpmnElement + '\'' +
                ", expanded=" + expanded +
                ", bounds=" + bounds +
                '}';
    }
}
