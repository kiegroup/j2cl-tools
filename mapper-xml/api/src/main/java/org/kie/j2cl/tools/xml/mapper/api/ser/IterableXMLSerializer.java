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

package org.kie.j2cl.tools.xml.mapper.api.ser;

import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for {@link Iterable}.
 *
 * @param <T> Type of the elements inside the {@link Iterable}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class IterableXMLSerializer<I extends Iterable<T>, T> extends XMLSerializer<I> {

  protected final XMLSerializer<T> serializer;

  /**
   * Constructor for IterableXMLSerializer.
   *
   * @param serializer {@link XMLSerializer} used to serialize the objects inside the {@link
   *     Iterable}.
   */
  protected IterableXMLSerializer(XMLSerializer<T> serializer) {
    if (null == serializer) {
      throw new IllegalArgumentException("serializer cannot be null");
    }
    this.serializer = serializer;
  }

  /**
   * newInstance
   *
   * @param serializer {@link XMLSerializer} used to serialize the objects inside the {@link
   *     Iterable}
   * @param <I> Type of the {@link Iterable}
   * @return a new instance of {@link IterableXMLSerializer}
   */
  public static <I extends Iterable<?>> IterableXMLSerializer<I, ?> newInstance(
      XMLSerializer<?> serializer) {
    return new IterableXMLSerializer(serializer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(I value) {
    return null == value || !value.iterator().hasNext();
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, I values, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    Iterator<T> iterator = values.iterator();

    if (!iterator.hasNext()) {
      if (ctx.isWriteEmptyXMLArrays()) {
        writer.beginArray();
        writer.endArray();
      } else {
        writer.nullValue();
      }
      return;
    }

    writer.beginArray();
    while (iterator.hasNext()) {
      serializer.setParent(parent).serialize(writer, iterator.next(), ctx, params);
    }
    writer.endArray();
  }
}
