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
package org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection;

import java.util.Map;
import java.util.Objects;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class StringMap {

  private Map<String, String> map;

  private String checkNewLine;

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StringMap)) {
      return false;
    }
    StringMap stringMap = (StringMap) o;
    return Objects.equals(getMap(), stringMap.getMap());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMap());
  }

  public String getCheckNewLine() {
    return checkNewLine;
  }

  public void setCheckNewLine(String checkNewLine) {
    this.checkNewLine = checkNewLine;
  }
}
