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
public abstract class AbstractArray2dXMLDeserializer<C> extends XMLDeserializer<C> {

  private final List<List<C>> list = new ArrayList<>();

  /**
   * Deserializes the array into a {@link java.util.List}. We need the length of the array before
   * creating it.
   *
   * @param reader reader
   * @param ctx context of the deserialization process
   * @param deserializer deserializer for element inside the array
   * @param params Parameters for the deserializer
   * @param <C> type of the element inside the array
   * @return a list containing all the elements of the array
   */
  protected <C> List<List<C>> deserializeIntoList(
      XMLReader reader,
      XMLDeserializationContext ctx,
      Function<String, XMLDeserializer<C>> deserializer,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    return doDeserializeIntoList(reader, ctx, deserializer, params);
  }

  /**
   * doDeserializeIntoList
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param deserializer a {@link XMLDeserializer} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @param <C> a C object.
   * @return a {@link java.util.List} object.
   */
  protected <C> List<List<C>> doDeserializeIntoList(
      XMLReader reader,
      XMLDeserializationContext ctx,
      Function<String, XMLDeserializer<C>> deserializer,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    reader.next();
    List temp = new ArrayList<>();
    int counter = 0;

    while (reader.hasNext()) {
      if (reader.peek() == 1) {
        counter++;
        C val =
            deserializer
                .apply(inheritanceChooser.get().apply(reader))
                .deserialize(reader, ctx, params);
        temp.add(val);
      }
      if (reader.peek() == 2) {
        counter--;
      }
      if (counter < 0) {
        list.add(temp);
        break;
      }
      reader.next();
    }
    return (List<List<C>>) (Object) list;
  }
}
