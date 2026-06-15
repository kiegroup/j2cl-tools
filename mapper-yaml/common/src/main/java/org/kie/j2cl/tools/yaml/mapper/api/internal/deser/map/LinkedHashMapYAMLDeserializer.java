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

import java.util.LinkedHashMap;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;

/**
 * Default {@link YAMLDeserializer} implementation for {@link LinkedHashMap}.
 *
 * <p>Cannot be overriden. Use {@link BaseMapYAMLDeserializer}.
 *
 * @param <V> Type of the values inside the {@link LinkedHashMap}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class LinkedHashMapYAMLDeserializer<V>
    extends BaseMapYAMLDeserializer<LinkedHashMap<String, V>, V> {

  /** @param valueDeserializer {@link YAMLDeserializer} used to deserialize the values. */
  private LinkedHashMapYAMLDeserializer(YAMLDeserializer<V> valueDeserializer) {
    super(valueDeserializer);
  }

  /**
   * newInstance
   *
   * @param valueDeserializer {@link YAMLDeserializer} used to deserialize the values.
   * @param <V> Type of the values inside the {@link LinkedHashMap}
   * @return a new instance of {@link LinkedHashMapYAMLDeserializer}
   */
  public static <K, V> LinkedHashMapYAMLDeserializer<V> newInstance(
      YAMLDeserializer<V> valueDeserializer) {
    return new LinkedHashMapYAMLDeserializer<>(valueDeserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected LinkedHashMap<String, V> newMap() {
    return new LinkedHashMap<>();
  }
}
