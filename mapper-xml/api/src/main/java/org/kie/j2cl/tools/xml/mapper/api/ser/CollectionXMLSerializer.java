/*
 * Copyright 2015 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.ser;

import java.util.Collection;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.BasicArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for {@link Collection}.
 *
 * @param <T> Type of the elements inside the {@link Collection}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class CollectionXMLSerializer<C extends Collection<T>, T>
    extends BasicArrayXMLSerializer<C> {

  private final Function<Class, XMLSerializer<T>> serializer;

  /**
   * Constructor for CollectionXMLSerializer.
   *
   * @param serializer {@link XMLSerializer} used to serialize the objects inside the {@link
   *     Collection}.
   */
  protected CollectionXMLSerializer(
      Function<Class, XMLSerializer<T>> serializer, String propertyName) {
    if (null == serializer) {
      throw new IllegalArgumentException("serializer cannot be null");
    }
    if (null == propertyName) {
      throw new IllegalArgumentException("propertyName cannot be null");
    }
    this.serializer = serializer;
    this.propertyName = propertyName;
  }

  /**
   * newInstance
   *
   * @param serializer {@link XMLSerializer} used to serialize the objects inside the {@link
   *     Collection}.
   * @param <C> Type of the {@link Collection}
   * @return a new instance of {@link CollectionXMLSerializer}
   */
  public static <C extends Collection<?>> CollectionXMLSerializer<C, ?> newInstance(
      Function<Class, XMLSerializer<?>> serializer, String propertyName) {
    return new CollectionXMLSerializer(serializer, propertyName);
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, C values, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (isEmpty(values)) {
      if (ctx.isWriteEmptyXMLArrays() && isWrapCollections) {
        beginObject(writer, true);
        endObject(writer, true);
      }
      return;
    }
    beginObject(writer, isWrapCollections);
    for (T value :
        (Collection<T>) values) { // J2CL bug: work around is to cast values to the collection
      serializer
          .apply(value.getClass())
          .setParent(parent)
          .setPropertyName(propertyName)
          .serialize(writer, value, ctx, params);
    }
    endObject(writer, isWrapCollections);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(C value) {
    return null == value || value.isEmpty();
  }
}
