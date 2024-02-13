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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.company;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(XmlRootElementTest.class)
public class XmlRootElementTest {

  Employee_XMLMapperImpl mapperEmployee = Employee_XMLMapperImpl.INSTANCE;
  Company_XMLMapperImpl mapperCompany = Company_XMLMapperImpl.INSTANCE;
  Department_XMLMapperImpl mapperDepartment = Department_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeMapperEmployee() throws XMLStreamException {
    Employee test = new Employee();
    test.setName("ANY");

    assertEquals(test, mapperEmployee.read(mapperEmployee.write(test)));
  }

  @Test
  public void testDeserializeMapperCompany() throws XMLStreamException {
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
    Employee employee1 = new Employee();
    employee1.setName("Employee 1");

    Employee employee2 = new Employee();
    employee2.setName("Employee 2");

    List<Employee> employeeList = new ArrayList<>();
    employeeList.add(employee1);
    employeeList.add(employee2);

    test.setEmployeeList(employeeList);

    String xml = mapperDepartment.write(test);

    assertEquals(test.getName(), mapperDepartment.read(xml).getName());
    assertEquals(
        test.getEmployeeList().size(), mapperDepartment.read(xml).getEmployeeList().size());
    assertEquals(test, mapperDepartment.read(xml));
    assertEquals(
        test.getEmployeeList().size(),
        mapperDepartment.read(mapperDepartment.write(test)).getEmployeeList().size());
    assertEquals(test, mapperDepartment.read(mapperDepartment.write(test)));
  }
}
