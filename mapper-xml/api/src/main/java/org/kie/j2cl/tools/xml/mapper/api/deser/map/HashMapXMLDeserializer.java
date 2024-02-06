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

import java.util.HashMap;
import java.util.function.Function;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.HashMap}.
 *
 * <p>Cannot be overriden. Use {@link BaseMapXMLDeserializer}.
 *
 * @param <K> Type of the keys inside the {@link java.util.HashMap}
 * @param <V> Type of the values inside the {@link java.util.HashMap}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class HashMapXMLDeserializer<K, V>
    extends BaseMapXMLDeserializer<HashMap<K, V>, K, V> {

  /**
   * newInstance
   *
   * @param keyDeserializer {@link XMLDeserializer} used to deserialize the keys.
   * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
   * @param <K> Type of the keys inside the {@link java.util.HashMap}
   * @param <V> Type of the values inside the {@link java.util.HashMap}
   * @return a new instance of {@link HashMapXMLDeserializer}
   */
  public static <K, V> HashMapXMLDeserializer<K, V> newInstance(
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer) {
    return new HashMapXMLDeserializer<>(keyDeserializer, valueDeserializer);
  }

  /**
   * @param keyDeserializer {@link XMLDeserializer} used to deserialize the keys.
   * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
   */
  private HashMapXMLDeserializer(
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer) {
    super(keyDeserializer, valueDeserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected HashMap<K, V> newMap() {
    return new HashMap<>();
  }
}
