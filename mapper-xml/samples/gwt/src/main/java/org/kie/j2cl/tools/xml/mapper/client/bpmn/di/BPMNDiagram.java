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
package org.kie.j2cl.tools.xml.mapper.client.bpmn.di;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BPMNDiagram {

    @XmlAttribute
    private String id;

    private List<BPMNPlane> planes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BPMNPlane> getPlanes() {
        return planes;
    }

    public void setPlanes(List<BPMNPlane> planes) {
        this.planes = planes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BPMNDiagram)) {
            return false;
        }
        BPMNDiagram that = (BPMNDiagram) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPlanes(), that.getPlanes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlanes());
    }

    @Override
    public String toString() {

        StringBuilder planesToString = new StringBuilder();
        if (planes != null) {
            planes.stream().map(elm -> "</br> &nbsp;&nbsp;&nbsp;&nbsp;" + elm.toString()).forEach(elm -> planesToString.append(elm));
        }


        return "BPMNDiagram{" +
                "id='" + id + '\'' +
                "</br> planes=" + planesToString +
                '}';
    }
}
