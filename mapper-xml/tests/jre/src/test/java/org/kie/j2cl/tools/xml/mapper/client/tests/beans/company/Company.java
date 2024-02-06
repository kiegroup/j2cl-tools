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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.company;

import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

/** @author Dmitrii Tikhomirov Created by treblereel 4/2/20 */
@XMLMapper
public class Company {

  private Employee ceo;
  private Address address;
  private List<Department> departmentList;

  @Override
  public int hashCode() {
    return Objects.hash(getCeo(), getAddress(), getDepartmentList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Company)) {
      return false;
    }
    Company company = (Company) o;
    return Objects.equals(getCeo(), company.getCeo())
        && Objects.equals(getAddress(), company.getAddress())
        && Objects.equals(getDepartmentList(), company.getDepartmentList());
  }

  public Employee getCeo() {
    return ceo;
  }

  public void setCeo(Employee ceo) {
    this.ceo = ceo;
  }

  public Address getAddress() {
    return address;
  }

  public List<Department> getDepartmentList() {
    return departmentList;
  }

  public void setDepartmentList(List<Department> departmentList) {
    this.departmentList = departmentList;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
