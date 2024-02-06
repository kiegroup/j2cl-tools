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

package org.kie.j2cl.tools.yaml.tests.annotations.customtypeser;

import java.util.Locale;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;

public class HolderSerializer
    implements YAMLSerializer<ValueHolder>, YAMLDeserializer<ValueHolder> {

  @Override
  public void serialize(
      YamlMapping writer, String propertyName, ValueHolder value, YAMLSerializationContext ctx) {
    writer.addScalarNode(propertyName, value.getValue().toUpperCase(Locale.ROOT));
  }

  @Override
  public void serialize(YamlSequence writer, ValueHolder value, YAMLSerializationContext ctx) {
    writer.addScalarNode(value.getValue().toUpperCase(Locale.ROOT));
  }

  @Override
  public ValueHolder deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
      throws YAMLDeserializationException {
    return deserialize(yaml.getNode(key), ctx);
  }

  @Override
  public ValueHolder deserialize(YamlNode value, YAMLDeserializationContext ctx) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    ValueHolder holder = new ValueHolder();
    holder.setValue(value.<String>asScalar().value().toLowerCase(Locale.ROOT));
    return holder;
  }
}
