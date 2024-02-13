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

package org.kie.j2cl.tools.xml.mapper.client.tests.collections;

import java.util.List;
import java.util.Map;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Person;

@XMLMapper
public class PersonMap {

  private Map<Person, List<Person>> map;

  @Override
  public int hashCode() {
    return getMap() != null ? getMap().hashCode() : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonMap)) return false;

    PersonMap personMap = (PersonMap) o;

    return getMap() != null ? getMap().equals(personMap.getMap()) : personMap.getMap() == null;
  }

  public Map<Person, List<Person>> getMap() {
    return map;
  }

  public void setMap(Map<Person, List<Person>> map) {
    this.map = map;
  }
}
