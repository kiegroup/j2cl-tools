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

package org.kie.j2cl.tools.yaml.tests.collection.customser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;

@YAMLMapper
public class WhenThenRule {

  @YamlTypeSerializer(WhenThenRuleThenSerializer.class)
  @YamlTypeDeserializer(WhenThenRuleThenSerializer.class)
  private List<Object> when;

  @YamlTypeSerializer(WhenThenRuleThenSerializer.class)
  @YamlTypeDeserializer(WhenThenRuleThenSerializer.class)
  private Object then;

  @YamlTypeSerializer(WhenThenRuleThenSerializer.class)
  @YamlTypeDeserializer(WhenThenRuleThenSerializer.class)
  private Object[] off;

  public List<Object> getWhen() {
    return when;
  }

  public Object getThen() {
    return then;
  }

  public void setWhen(List<Object> when) {
    this.when = when;
  }

  public void setThen(Object then) {
    this.then = then;
  }

  public Object[] getOff() {
    return off;
  }

  public void setOff(Object[] off) {
    this.off = off;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WhenThenRule that = (WhenThenRule) o;
    return Objects.equals(when, that.when)
        && Objects.equals(then, that.then)
        && Arrays.equals(off, that.off);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(when, then);
    result = 31 * result + Arrays.hashCode(off);
    return result;
  }
}
