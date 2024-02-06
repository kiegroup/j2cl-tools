/*
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
package org.kie.j2cl.tools.xml.mapper.api.stream;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;

public interface XMLIterator {

  <T> T iterateOverBean(
      XMLReader reader,
      PropertyNameScanner<T> scanner,
      T instance,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params)
      throws XMLStreamException;

  <T> Collection<T> iterateOverCollection(
      XMLReader reader,
      Collection<T> collection,
      Scanner<T> scanner,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params,
      boolean isWrapCollections)
      throws XMLStreamException;

  <K, V> Map<K, V> doDeserializeMap(
      XMLReader reader,
      Map<K, V> collection,
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params)
      throws XMLStreamException;

  @FunctionalInterface
  interface Scanner<T> {

    T accept(XMLReader reader, XMLDeserializationContext ctx, T instance) throws XMLStreamException;
  }

  @FunctionalInterface
  interface PropertyNameScanner<T> {

    T accept(XMLReader reader, QName propertyName, XMLDeserializationContext ctx, T instance)
        throws XMLStreamException;
  }
}
