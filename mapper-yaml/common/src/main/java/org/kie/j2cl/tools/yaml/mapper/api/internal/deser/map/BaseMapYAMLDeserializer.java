/*
 * Copyright 2013 Nicolas Morel
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

package org.kie.j2cl.tools.yaml.mapper.api.internal.deser.map;

import java.util.Map;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;

/**
 * Base {@link YAMLDeserializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseMapYAMLDeserializer<M extends Map<String, V>, V>
    implements YAMLDeserializer<M> {

  /** {@link YAMLDeserializer} used to deserialize the values. */
  protected final YAMLDeserializer<V> valueDeserializer;

  /**
   * Constructor for BaseMapYAMLDeserializer.
   *
   * @param valueDeserializer {@link YAMLDeserializer} used to deserialize the values.
   */
  protected BaseMapYAMLDeserializer(YAMLDeserializer<V> valueDeserializer) {
    if (null == valueDeserializer) {
      throw new IllegalArgumentException("valueDeserializer cannot be null");
    }
    this.valueDeserializer = valueDeserializer;
  }

  /** {@inheritDoc} */
  @Override
  public M deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {
    YamlNode node = yaml.getNode(key);
    if (null == node) {
      return null;
    }
    return deserialize(node, ctx);
  }

  @Override
  public M deserialize(YamlNode node, YAMLDeserializationContext ctx) {
    M map = newMap();
    for (String key : node.asMapping().keys()) {
      YamlNode temp = node.asMapping().getNode(key);
      V value = valueDeserializer.deserialize(temp, ctx);
      map.put(key, value);
    }
    return map;
  }

  /**
   * Instantiates a new map for deserialization process.
   *
   * @return the new map
   */
  protected abstract M newMap();
}
