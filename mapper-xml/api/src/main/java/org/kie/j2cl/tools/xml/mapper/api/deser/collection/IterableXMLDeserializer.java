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

package org.kie.j2cl.tools.xml.mapper.api.deser.collection;

import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.lang.Iterable}. The
 * deserialization process returns an {@link java.util.ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link java.lang.Iterable}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class IterableXMLDeserializer<T> extends BaseIterableXMLDeserializer<Iterable<T>, T> {

  /**
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     Iterable}.
   */
  private IterableXMLDeserializer(Function<String, XMLDeserializer<T>> deserializer) {
    super(deserializer);
  }

  /**
   * newInstance
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     java.lang.Iterable}.
   * @param <T> Type of the elements inside the {@link java.lang.Iterable}
   * @return a new instance of {@link IterableXMLDeserializer}
   */
  public static <T> IterableXMLDeserializer<T> newInstance(
      Function<String, XMLDeserializer<T>> deserializer) {
    return new IterableXMLDeserializer<>(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  public Iterable<T> doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    /*        if (XMLToken.BEGIN_ARRAY == reader.peek()) {

        Collection<T> result = new ArrayList<T>();

        reader.beginArray();
        while (XMLToken.END_ARRAY != reader.peek()) {
            result.add(deserializer.deserialize(reader, ctx, params));
        }
        reader.endArray();
        return result;

    } else if (ctx.isAcceptSingleValueAsArray()) {

        Collection<T> result = new ArrayList<T>();
        result.add(deserializer.deserialize(reader, ctx, params));
        return result;

    } else {
        throw ctx.traceError("Cannot deserialize a java.lang.Iterable out of " + reader.peek() + " token", reader);
    }*/
    throw new UnsupportedOperationException();
  }
}
