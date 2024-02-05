/*
 * Copyright 2014 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.deser.array.dd;

import java.util.List;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for 2D array.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class Array2dXMLDeserializer<T> extends AbstractArray2dXMLDeserializer<T[][]> {

  public interface Array2dCreator<T> {

    T[][] create(int first, int second);
  }

  /**
   * newInstance
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the array.
   * @param arrayCreator {@link Array2dXMLDeserializer.Array2dCreator} used to create a new array
   * @param <T> Type of the elements inside the {@link java.util.AbstractCollection}
   * @return a new instance of {@link Array2dXMLDeserializer}
   */
  public static <T> Array2dXMLDeserializer<T> newInstance(
      Function<String, XMLDeserializer<T>> deserializer, Array2dCreator<T> arrayCreator) {
    return new Array2dXMLDeserializer<>(deserializer, arrayCreator);
  }

  private final Function<String, XMLDeserializer<T>> deserializer;

  private final Array2dCreator<T> array2dCreator;

  /**
   * Constructor for Array2dXMLDeserializer.
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the array.
   * @param array2dCreator {@link Array2dXMLDeserializer.Array2dCreator} used to create a new array
   */
  protected Array2dXMLDeserializer(
      Function<String, XMLDeserializer<T>> deserializer, Array2dCreator<T> array2dCreator) {
    if (null == deserializer) {
      throw new IllegalArgumentException("deserializer cannot be null");
    }
    if (null == array2dCreator) {
      throw new IllegalArgumentException("Cannot deserialize an array without an array2dCreator");
    }
    this.deserializer = deserializer;
    this.array2dCreator = array2dCreator;
  }

  /** {@inheritDoc} */
  @Override
  protected T[][] doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<List<T>> list = deserializeIntoList(reader, ctx, deserializer, params);

    if (list.isEmpty()) {
      return array2dCreator.create(0, 0);
    }

    List<T> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return array2dCreator.create(list.size(), 0);
    }

    T[][] array = array2dCreator.create(list.size(), firstList.size());

    int i = 0;
    for (List<T> innerList : list) {
      array[i] = innerList.toArray(array[i]);
      i++;
    }
    return array;
  }
}
