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

package org.kie.j2cl.tools.xml.mapper.api.deser.map;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.function.Function;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.AbstractMap}. The
 * deserialization process returns a {@link java.util.LinkedHashMap}.
 *
 * <p>Cannot be overriden. Use {@link BaseMapXMLDeserializer}.
 *
 * @param <K> Type of the keys inside the {@link java.util.AbstractMap}
 * @param <V> Type of the values inside the {@link java.util.AbstractMap}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class AbstractMapXMLDeserializer<K, V>
    extends BaseMapXMLDeserializer<AbstractMap<K, V>, K, V> {

  /**
   * @param keyDeserializer {@link XMLDeserializer} used to deserialize the keys.
   * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
   */
  private AbstractMapXMLDeserializer(
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer) {
    super(keyDeserializer, valueDeserializer);
  }

  /**
   * newInstance
   *
   * @param keyDeserializer {@link XMLDeserializer} used to deserialize the keys.
   * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
   * @param <K> Type of the keys inside the {@link java.util.AbstractMap}
   * @param <V> Type of the values inside the {@link java.util.AbstractMap}
   * @return a new instance of {@link AbstractMapXMLDeserializer}
   */
  public static <K, V> AbstractMapXMLDeserializer<K, V> newInstance(
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer) {
    return new AbstractMapXMLDeserializer<>(keyDeserializer, valueDeserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractMap<K, V> newMap() {
    return new LinkedHashMap<>();
  }
}
