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

package org.kie.j2cl.tools.xml.mapper.api.deser.array;

import java.util.List;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for array.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class ArrayXMLDeserializer<T> extends AbstractArrayXMLDeserializer<T[]> {

  private final Function<String, XMLDeserializer<T>> deserializer;
  private final ArrayCreator<T> arrayCreator;

  /**
   * Constructor for ArrayXMLDeserializer.
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the array.
   * @param arrayCreator {@link ArrayXMLDeserializer.ArrayCreator} used to create a new array
   */
  protected ArrayXMLDeserializer(
      Function<String, XMLDeserializer<T>> deserializer, ArrayCreator<T> arrayCreator) {
    if (null == deserializer) {
      throw new IllegalArgumentException("deserializer cannot be null");
    }
    if (null == arrayCreator) {
      throw new IllegalArgumentException("Cannot deserialize an array without an arrayCreator");
    }
    this.deserializer = deserializer;
    this.arrayCreator = arrayCreator;
  }

  /**
   * newInstance
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the array.
   * @param arrayCreator {@link ArrayXMLDeserializer.ArrayCreator} used to create a new array
   * @param <T> Type of the elements inside the {@link java.util.AbstractCollection}
   * @return a new instance of {@link ArrayXMLDeserializer}
   */
  public static <T> ArrayXMLDeserializer<T> newInstance(
      Function<String, XMLDeserializer<T>> deserializer, ArrayCreator<T> arrayCreator) {
    return new ArrayXMLDeserializer<>(deserializer, arrayCreator);
  }

  /** {@inheritDoc} */
  @Override
  public T[] doDeserializeArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<T> list = deserializeIntoList(reader, ctx, deserializer, params);
    if (list == null) {
      return null;
    }
    return list.toArray(arrayCreator.create(list.size()));
  }

  /** {@inheritDoc} */
  @Override
  protected T[] doDeserializeSingleArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    T[] result = arrayCreator.create(1);
    result[0] =
        deserializer.apply(inheritanceChooser.get().apply(reader)).deserialize(reader, ctx, params);
    return result;
  }

  @FunctionalInterface
  public interface ArrayCreator<T> {

    T[] create(int length);
  }
}
