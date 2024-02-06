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

package org.kie.j2cl.tools.xml.mapper.api.ser.map;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <K> Type of the keys inside the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class MapXMLSerializer<M extends Map<K, V>, K, V> extends XMLSerializer<M> {

  private final Function<Class, XMLSerializer<K>> keySerializer;
  private final Function<Class, XMLSerializer<V>> valueSerializer;
  protected final String propertyName;

  /**
   * Constructor for MapXMLSerializer.
   *
   * @param keySerializer {@link XMLSerializer} used to serialize the keys.
   * @param valueSerializer {@link XMLSerializer} used to serialize the values.
   */
  protected MapXMLSerializer(
      Function<Class, XMLSerializer<K>> keySerializer,
      Function<Class, XMLSerializer<V>> valueSerializer,
      String propertyName) {
    if (null == keySerializer) {
      throw new IllegalArgumentException("keySerializer cannot be null");
    }
    if (null == valueSerializer) {
      throw new IllegalArgumentException("valueSerializer cannot be null");
    }
    if (null == propertyName) {
      throw new IllegalArgumentException("valueSerializer cannot be null");
    }
    this.keySerializer = keySerializer;
    this.valueSerializer = valueSerializer;
    this.propertyName = propertyName;
  }

  /**
   * newInstance
   *
   * @param keySerializer {@link XMLSerializer} used to serialize the keys.
   * @param valueSerializer {@link XMLSerializer} used to serialize the values.
   * @param <M> Type of the {@link Map}
   * @return a new instance of {@link MapXMLSerializer}
   */
  public static <M extends Map<?, ?>> MapXMLSerializer<M, ?, ?> newInstance(
      Function<Class, XMLSerializer<?>> keySerializer,
      Function<Class, XMLSerializer<?>> valueSerializer,
      String propertyName) {
    return new MapXMLSerializer(keySerializer, valueSerializer, propertyName);
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, M values, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    serializeValues(writer, values, ctx, params);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(M value) {
    return null == value || value.isEmpty();
  }

  /**
   * serializeValues
   *
   * @param writer a {@link XMLWriter} object.
   * @param values a M object.
   * @param ctx a {@link XMLSerializationContext} object.
   * @param params a {@link XMLSerializerParameters} object.
   */
  public void serializeValues(
      XMLWriter writer, M values, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (!values.isEmpty()) {
      Map<K, V> map = values;
      if (ctx.isOrderMapEntriesByKeys() && !(values instanceof SortedMap<?, ?>)) {
        map = new TreeMap<>(map);
      }
      writer.beginObject(propertyName);
      for (Map.Entry<K, V> entry : map.entrySet()) {
        writer.beginObject("entry");
        String keyName = "key";
        String valueName = "value";
        writer.unescapeName(keyName);
        keySerializer
            .apply(entry.getKey().getClass())
            .setPropertyName(keyName)
            .setParent(this)
            .serialize(writer, entry.getKey(), ctx, params, true);

        writer.unescapeName(valueName);
        valueSerializer
            .apply(entry.getValue().getClass())
            .setPropertyName(valueName)
            .setParent(this)
            .serialize(writer, entry.getValue(), ctx, params, true);

        writer.endObject();
      }
      writer.endObject();
    }
  }
}
