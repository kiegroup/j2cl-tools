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
import jakarta.xml.bind.annotation.XmlRootElement;

import org.kie.j2cl.tools.xml.mapper.api.annotation.TargetNamespace;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.bpmn.di.BPMNDiagram;

@XMLMapper
@XmlRootElement(name = "bpmn2:definitions")
@TargetNamespace(prefix = "bpmn2", namespace = "http://www.omg.org/bpmn20")
public class Definitions {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;

    private List<ItemDefinition> itemDefinitions = new ArrayList<>();

    @XmlAttribute
    private String exporter = "jBPM Process Modeler";

    @XmlAttribute
    private String exporterVersion = "2.0";

    private Process process;

    @XmlAttribute(name = "BPMNDiagram")
    private BPMNDiagram bpmnDiagram;

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getItemDefinitions(), getExporter(), getExporterVersion(), getProcess(), getBpmnDiagram());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Definitions)) {
            return false;
        }
        Definitions that = (Definitions) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getItemDefinitions(), that.getItemDefinitions()) &&
                Objects.equals(getExporter(), that.getExporter()) &&
                Objects.equals(getExporterVersion(), that.getExporterVersion()) &&
                Objects.equals(getProcess(), that.getProcess()) &&
                Objects.equals(getBpmnDiagram(), that.getBpmnDiagram());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Definitions ").append(" id='").append(id)
                .append("', name='").append(name)
                .append("', exporter='").append(exporter)
                .append("', exporterVersion='").append(exporterVersion);

        if (itemDefinitions == null || itemDefinitions.isEmpty()) {
            sb.append(" no itemDefinitions\\n");
        } else {
            itemDefinitions.forEach(item ->{
                sb.append("</br>&nbsp;&nbsp;&nbsp;&nbsp;").append(item.toString());
            });
        }

        if(process != null) {
            sb.append("</br>&nbsp;&nbsp;&nbsp;&nbsp;").append(process.toString());
        }
        if(bpmnDiagram != null) {
            sb.append("</br>&nbsp;&nbsp;&nbsp;&nbsp;").append(bpmnDiagram.toString());
        }


        return sb.toString();

/*        return "Definitions " +
                "id='" + id + " " +
                ", name='" + name + '\'' +
                ", itemDefinitions=" + itemDefinitions +
                ", exporter='" + exporter + '\'' +
                ", exporterVersion='" + exporterVersion + '\'' +
                ", process=" + process +
                ", bpmnDiagram=" + bpmnDiagram +
                '}';*/
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

    public List<ItemDefinition> getItemDefinitions() {
        return itemDefinitions;
    }

    public void setItemDefinitions(List<ItemDefinition> itemDefinitions) {
        this.itemDefinitions = itemDefinitions;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getExporterVersion() {
        return exporterVersion;
    }

    public void setExporterVersion(String exporterVersion) {
        this.exporterVersion = exporterVersion;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public BPMNDiagram getBpmnDiagram() {
        return bpmnDiagram;
    }

    public void setBpmnDiagram(BPMNDiagram bpmnDiagram) {
        this.bpmnDiagram = bpmnDiagram;
    }

    public void setId(String id) {
        this.id = id;
    }
}
