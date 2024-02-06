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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.address.Address;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Company;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Company_XMLMapperImpl;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Department;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Department_XMLMapperImpl;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Employee;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.beans.company.Employee_XMLMapperImpl;

/** @author Dmitrii Tikhomirov Created by treblereel 4/1/20 */
@J2clTestInput(XmlRootElementTest.class)
public class XmlRootElementTest {

  Employee_XMLMapperImpl mapperEmployee = Employee_XMLMapperImpl.INSTANCE;
  Company_XMLMapperImpl mapperCompany = Company_XMLMapperImpl.INSTANCE;
  Department_XMLMapperImpl mapperDepartment = Department_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeMapperEmployee() throws XMLStreamException {
    Employee test = new Employee();
    test.setName("ANY");

    String xml = mapperEmployee.write(test);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><employee xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:bpsim=\"http://www.bpsim.org/schemas/1.0\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:drools=\"http://www.jboss.org/drools\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd http://www.jboss.org/drools drools.xsd http://www.bpsim.org/schemas/1.0 bpsim.xsd http://www.omg.org/spec/DD/20100524/DC DC.xsd http://www.omg.org/spec/DD/20100524/DI DI.xsd\" employee_name=\"ANY\"/>",
        xml);
    assertEquals(test, mapperEmployee.read(mapperEmployee.write(test)));
  }

  @Test
  public void testDeserializeMapperCompany() throws XMLStreamException {
    String pattern =
        "<?xml version='1.0' encoding='UTF-8'?><Company xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:bpsim=\"http://www.bpsim.org/schemas/1.0\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:drools=\"http://www.jboss.org/drools\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd http://www.jboss.org/drools drools.xsd http://www.bpsim.org/schemas/1.0 bpsim.xsd http://www.omg.org/spec/DD/20100524/DC DC.xsd http://www.omg.org/spec/DD/20100524/DI DI.xsd\"><ceo employee_name=\"CEO\"/><address xmlns=\"address\" street=\"1ST\"/><departmentList><departmentList department_name=\"IT\"/></departmentList></Company>";

    Company test = new Company();
    Employee ceo = new Employee();
    ceo.setName("CEO");

    Department department = new Department();
    department.setName("IT");
    List<Department> departments = new ArrayList<>();
    departments.add(department);

    Address address = new Address();
    address.setStreet("1ST");

    test.setCeo(ceo);
    test.setAddress(address);
    test.setDepartmentList(departments);

    String xml = mapperCompany.write(test);

    assertEquals(test, mapperCompany.read(mapperCompany.write(test)));

    // j2cl test fails in cli, but works in a browser
    // assertEquals("<?xml version='1.0' encoding='UTF-8'?><Company
    // xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
    // xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"
    // xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\"
    // xmlns:bpsim=\"http://www.bpsim.org/schemas/1.0\"
    // xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\"
    // xmlns:drools=\"http://www.jboss.org/drools\"
    // xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd
    // http://www.jboss.org/drools drools.xsd http://www.bpsim.org/schemas/1.0 bpsim.xsd
    // http://www.omg.org/spec/DD/20100524/DC DC.xsd http://www.omg.org/spec/DD/20100524/DI
    // DI.xsd\"><ceo employee_name=\"CEO\"/><address xmlns=\"address\"
    // street=\"1ST\"/><departmentList><departmentList
    // department_name=\"IT\"/></departmentList></Company>", xml);

    Company result = mapperCompany.read(xml);
    assertEquals(test.getAddress(), result.getAddress());
    assertEquals(test.getCeo(), result.getCeo());
    assertEquals(1, result.getDepartmentList().size());
    assertEquals(test.getDepartmentList().get(0), result.getDepartmentList().get(0));
    assertEquals(test, mapperCompany.read(mapperCompany.write(test)));
  }

  @Test
  public void testDeserializeMapperDepartment() throws XMLStreamException {
    Department test = new Department();
    test.setName("ZZ");
    Employee employee1 = new Employee();
    employee1.setName("Employee 1");

    Employee employee2 = new Employee();
    employee2.setName("Employee 2");

    List<Employee> employeeList = new ArrayList<>();
    employeeList.add(employee1);
    employeeList.add(employee2);

    test.setEmployeeList(employeeList);

    String xml = mapperDepartment.write(test);

    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><my_department xmlns=\"http://www.omg.org/bpmn20\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:bpsim=\"http://www.bpsim.org/schemas/1.0\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:drools=\"http://www.jboss.org/drools\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd http://www.jboss.org/drools drools.xsd http://www.bpsim.org/schemas/1.0 bpsim.xsd http://www.omg.org/spec/DD/20100524/DC DC.xsd http://www.omg.org/spec/DD/20100524/DI DI.xsd\" department_name=\"ZZ\"><employeeList employee_name=\"Employee 1\"/><employeeList employee_name=\"Employee 2\"/></my_department>",
        mapperDepartment.write(test));

    assertEquals(test.getName(), mapperDepartment.read(xml).getName());
    assertEquals(test, mapperDepartment.read(xml));
    assertEquals(
        test.getEmployeeList().size(),
        mapperDepartment.read(mapperDepartment.write(test)).getEmployeeList().size());
    assertEquals(test, mapperDepartment.read(mapperDepartment.write(test)));
  }
}
