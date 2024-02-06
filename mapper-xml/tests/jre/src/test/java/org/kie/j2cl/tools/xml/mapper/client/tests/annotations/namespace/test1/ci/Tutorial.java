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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.ci;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.namespace.test1.cl.Name;

/** @author Dmitrii Tikhomirov Created by treblereel 4/28/20 */
@XMLMapper
@XmlRootElement(name = "_tutorial", namespace = "http://ns")
public class Tutorial {

  private int id;
  // @XmlElement(namespace = "test1") //TODO check if name must be test1
  private List<Name> names;

  @XmlElementWrapper(name = "wrapper")
  @XmlElement(namespace = "http://www.cl")
  private List<Name> types;

  @XmlElement(namespace = "http://www.cl")
  private Name[] arrays;

  public Tutorial() {}

  public Tutorial(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public List<Name> getNames() {
    return names;
  }

  public void setNames(List<Name> names) {
    this.names = names;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Name> getTypes() {
    return types;
  }

  public void setTypes(List<Name> types) {
    this.types = types;
  }

  public Name[] getArrays() {
    return arrays;
  }

  public void setArrays(Name[] arrays) {
    this.arrays = arrays;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tutorial)) return false;

    Tutorial tutorial = (Tutorial) o;

    if (getId() != tutorial.getId()) return false;
    if (getNames() != null ? !getNames().equals(tutorial.getNames()) : tutorial.getNames() != null)
      return false;
    if (getTypes() != null ? !getTypes().equals(tutorial.getTypes()) : tutorial.getTypes() != null)
      return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(getArrays(), tutorial.getArrays());
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getNames() != null ? getNames().hashCode() : 0);
    result = 31 * result + (getTypes() != null ? getTypes().hashCode() : 0);
    result = 31 * result + Arrays.hashCode(getArrays());
    return result;
  }
}
