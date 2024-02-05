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
package org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.di;

import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** @author Dmitrii Tikhomirov Created by treblereel 4/6/20 */
public class BPMNDiagram {

  @XmlAttribute private String id;

  private List<BPMNPlane> planes = new ArrayList<>();

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPlanes());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BPMNDiagram)) {
      return false;
    }
    BPMNDiagram diagram = (BPMNDiagram) o;

    return Objects.equals(getId(), diagram.getId())
        && Objects.equals(getPlanes(), diagram.getPlanes());
  }

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
}
