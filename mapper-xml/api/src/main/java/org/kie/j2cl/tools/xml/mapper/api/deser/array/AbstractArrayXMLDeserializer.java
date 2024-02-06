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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Base implementation of {@link XMLDeserializer} for array.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractArrayXMLDeserializer<T> extends XMLDeserializer<T> {

  private List collection = new ArrayList();

  /**
   * Deserializes the array into a {@link java.util.List}. We need the length of the array before
   * creating it.
   *
   * @param reader reader
   * @param ctx context of the deserialization process
   * @param params Parameters for the deserializer
   * @return a list containing all the elements of the array /** {@inheritDoc}
   */
  @Override
  public T doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    return doDeserializeArray(reader, ctx, params);
  }

  /**
   * doDeserializeArray
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @return a T object.
   */
  protected abstract T doDeserializeArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException;

  /**
   * doDeserializeSingleArray
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @return a T object.
   */
  protected abstract T doDeserializeSingleArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException;

  protected <T> List<T> deserializeIntoList(
      XMLReader reader,
      XMLDeserializationContext ctx,
      Function<String, XMLDeserializer<T>> deserializer,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    return (List<T>)
        ctx.iterator()
            .iterateOverCollection(
                reader,
                collection,
                (reader1, ctx1, instance) -> {
                  T bean =
                      deserializer
                          .apply(inheritanceChooser.get().apply(reader1))
                          .deserialize(reader1, ctx1, params);
                  collection.add(bean);
                  return null;
                },
                ctx,
                params,
                isWrapCollections);
  }
}
