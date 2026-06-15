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
package org.kie.j2cl.tools.yaml.tests.collection.map;

import java.util.List;
import java.util.Map;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
public class Tested {

  private Map<String, String> map;

  private Map<String, ValueHolder> valueHolderMap;

  private Map<String, List<String>> listMap;

  public Map<String, List<String>> getListMap() {
    return listMap;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public Map<String, ValueHolder> getValueHolderMap() {
    return valueHolderMap;
  }

  public void setListMap(Map<String, List<String>> listMap) {
    this.listMap = listMap;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public void setValueHolderMap(Map<String, ValueHolder> valueHolderMap) {
    this.valueHolderMap = valueHolderMap;
  }
}
