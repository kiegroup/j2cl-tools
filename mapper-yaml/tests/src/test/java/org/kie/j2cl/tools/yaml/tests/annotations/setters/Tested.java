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

package org.kie.j2cl.tools.yaml.tests.annotations.setters;

import java.util.Objects;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlGetter;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlSetter;

@YAMLMapper
public class Tested {

  private String value;

  public Tested() {}

  public Tested(String value) {
    this.value = value;
  }

  @YamlGetter("value")
  public String value() {
    return value;
  }

  @YamlSetter("value")
  public void value(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Tested tested = (Tested) object;
    return Objects.equals(value, tested.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
