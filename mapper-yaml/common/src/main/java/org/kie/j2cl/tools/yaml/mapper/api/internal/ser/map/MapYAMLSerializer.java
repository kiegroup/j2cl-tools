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

package org.kie.j2cl.tools.yaml.mapper.api.internal.ser.map;

import java.util.Map;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.AbstractYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;

/**
 * Default {@link AbstractYAMLSerializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class MapYAMLSerializer<M extends Map<String, V>, V> extends AbstractYAMLSerializer<M> {

  protected final YAMLSerializer<V> valueSerializer;
  protected final String propertyName;

  /**
   * Constructor for MapYAMLSerializer.
   *
   * @param valueSerializer {@link AbstractYAMLSerializer} used to serialize the values.
   */
  protected MapYAMLSerializer(YAMLSerializer<V> valueSerializer, String propertyName) {
    if (null == valueSerializer) {
      throw new IllegalArgumentException("valueSerializer cannot be null");
    }
    if (null == propertyName) {
      throw new IllegalArgumentException("valueSerializer cannot be null");
    }
    this.valueSerializer = valueSerializer;
    this.propertyName = propertyName;
  }

  /**
   * newInstance
   *
   * @param valueSerializer {@link AbstractYAMLSerializer} used to serialize the values.
   * @param <M> Type of the {@link Map}
   * @return a new instance of {@link MapYAMLSerializer}
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <M extends Map<String, ?>> MapYAMLSerializer<M, ?> newInstance(
      YAMLSerializer<?> valueSerializer, String propertyName) {
    return new MapYAMLSerializer(valueSerializer, propertyName);
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(YamlMapping writer, M values, YAMLSerializationContext ctx) {
    serializeValues(writer, values, ctx);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(M value) {
    return null == value || value.isEmpty();
  }

  /**
   * serializeValues
   *
   * @param writer a {@link YamlMapping} object.
   * @param values a M object.
   * @param ctx a {@link YAMLSerializationContext} object.
   */
  public void serializeValues(YamlMapping writer, M values, YAMLSerializationContext ctx) {
    if (!values.isEmpty()) {
      for (Map.Entry<String, V> entry : values.entrySet()) {
        String key = entry.getKey();
        V value = entry.getValue();
        if (null == value && !ctx.isSerializeNulls()) {
          continue;
        }
        valueSerializer.serialize(writer, key, value, ctx);
      }
    }
  }

  @Override
  public void serialize(YamlSequence writer, M map, YAMLSerializationContext ctx) {
    if (!map.isEmpty()) {
      YamlMapping yamlMapping = writer.addMappingNode();
      serializeValues(yamlMapping, map, ctx);
    }
  }
}
