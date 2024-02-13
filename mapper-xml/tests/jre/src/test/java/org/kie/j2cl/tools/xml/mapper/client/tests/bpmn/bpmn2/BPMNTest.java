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
package org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn2;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.dc.Bounds;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.di.BPMNDiagram;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.di.BPMNPlane;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.bpmn.di.BPMNShape;
import org.kie.j2cl.tools.xml.mapper.client.tests.bpmn.drools.MetaData;

@J2clTestInput(BPMNTest.class)
public class BPMNTest {

  private static final Definitions_XMLMapperImpl mapper = Definitions_XMLMapperImpl.INSTANCE;

  @Test
  public void test() throws XMLStreamException {

    Definitions tested = new Definitions();
    tested.setId("ad04f8e4-9ac1-11ea-bb37-0242ac130002");
    tested.setExporter("jBPM Process Modeler");
    tested.setExporterVersion("2.0");

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setId("_DataObjectItem");
    itemDefinition.setStructureRef("String");
    tested.getItemDefinitions().add(itemDefinition);

    Process process = new Process();
    process.setId("twdo.twodosubprocess");
    process.setName("twodosubprocess");
    process.setPackageName("com.myspace.twdo");
    process.setAdHoc(false);
    process.setVersion(1.0);
    process.setExecutable(true);

    UserTask userTask = new UserTask();

    List<BPMNViewDefinition> definitionList = new ArrayList<>();
    definitionList.add(userTask);

    List<Data> result = new ArrayList<>();
    result.add(new DataInput(userTask.getId(), "TaskNameInputX", "getTaskName"));
    result.add(new DataInput(userTask.getId(), "SkippableInputX", "getSkippable"));
    result.add(new DataInput(userTask.getId(), "DescriptionInputX", "getDescription"));
    result.add(new DataInput(userTask.getId(), "PriorityInputX", "getPriority"));
    result.add(new DataInput(userTask.getId(), "ContentInputX", "getContent"));

    List<MetaData> metaDataList = new ArrayList<>();
    metaDataList.add(new MetaData("elementname", "getTaskName"));
    metaDataList.add(new MetaData("customAsync", "getIsAsync"));
    metaDataList.add(new MetaData("customAutoStart", "getAdHocAutostart"));
    userTask.setExtensionElements(metaDataList);

    InputSet inputSet = new InputSet();
    List<String> list = new ArrayList<>();
    list.add(userTask.getId() + "_TaskNameInputX");
    list.add(userTask.getId() + "_SkippableInputX");
    list.add(userTask.getId() + "_CommentInputX");
    list.add(userTask.getId() + "_DescriptionInputX");
    list.add(userTask.getId() + "_PriorityInputX");
    list.add(userTask.getId() + "_ContentInputX");
    inputSet.setSet(list);
    result.add(inputSet);

    userTask.setIoSpecification(result);

    process.setDefinitionList(definitionList);

    SubProcess subProcess = new SubProcess();
    subProcess.setId("_7503B170-81DB-47F5-BAF3-F67957B95DF7");
    subProcess.setName("Event Sub-process");
    subProcess.setTriggeredByEvent(true);

    process.getSubProcesses().add(subProcess);

    MetaData meta = new MetaData();
    meta.setName("elementname");
    meta.setMetaValue("Event Sub-process");
    subProcess.getExtensionElements().add(meta);

    tested.setProcess(process);

    DataObject dataObject = new DataObject();
    dataObject.setId("DataObject");
    dataObject.setName("DataObject");
    dataObject.setItemSubjectRef("_DataObjectItem");
    process.getDataObjects().add(dataObject);

    DataObjectReference dataObjectReference = new DataObjectReference();
    dataObjectReference.setId("_D7D714AE-A0C9-4A33-89C1-25300898967E");
    dataObjectReference.setDataObjectRef(dataObject);
    process.getDataObjectReferences().add(dataObjectReference);

    BPMNDiagram diagram = new BPMNDiagram();
    diagram.setId("_QB3JgVY5Eeq0CdSiRkduQA");
    tested.setBpmnDiagram(diagram);

    BPMNPlane plane = new BPMNPlane();
    plane.setId("_QB3JglY5Eeq0CdSiRkduQA");
    plane.setBpmnElement("twdo.twodosubprocess");
    diagram.getPlanes().add(plane);

    BPMNShape shape = new BPMNShape();
    shape.setId("shape__7503B170-81DB-47F5-BAF3-F67957B95DF7");
    shape.setBpmnElement("_7503B170-81DB-47F5-BAF3-F67957B95DF7");
    shape.setExpanded(true);
    plane.getShapes().add(shape);

    Bounds bounds = new Bounds();
    bounds.setHeight(253.0);
    bounds.setWidth(653.0);
    bounds.setX(488.0);
    bounds.setY(210.0);
    shape.setBounds(bounds);

    String xml = mapper.write(tested);
    Definitions encoded = mapper.read(xml);
    assertEquals(xml, mapper.write(encoded));

    xml = mapper.write(tested);
    // System.out.println("RESULT \n" + xml);
    encoded = mapper.read(xml);
    assertEquals(tested, encoded);
    assertEquals(xml, mapper.write(encoded));
  }

  @Test
  public void testUnwrappedCollections() throws XMLStreamException {
    Definitions definition = new Definitions();

    List<ItemDefinition> itemDefinitionList = new ArrayList<>();
    definition.setItemDefinitions(itemDefinitionList);

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setId("_DataObjectItem");
    itemDefinition.setStructureRef("String");
    definition.getItemDefinitions().add(itemDefinition);

    itemDefinition = new ItemDefinition();
    itemDefinition.setId("_DataObjectItem_1");
    itemDefinition.setStructureRef("String_1");
    definition.getItemDefinitions().add(itemDefinition);

    Definitions_XMLMapperImpl mapper = new Definitions_XMLMapperImpl();
    String xml = mapper.write(definition);
    Definitions result = mapper.read(mapper.write(definition));
    assertEquals(definition, result);
    assertEquals(xml, mapper.write(mapper.read(mapper.write(result))));
    assertEquals(xml, mapper.write(mapper.read(mapper.write(result))));
  }
}
