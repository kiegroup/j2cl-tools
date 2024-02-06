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
package org.kie.j2cl.tools.xml.mapper.api.stream.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLIterator;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

public class DefaultXMLIterator implements XMLIterator {

  private final QName xsiType = new QName("http://www.w3.org/2001/XMLSchema-instance", "type");

  @Override
  public <T> T iterateOverBean(
      XMLReader reader,
      PropertyNameScanner<T> scanner,
      T instance,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    QName rootNode = reader.peekNodeName();
    reader.next();
    if (reader.peek() == XMLStreamConstants.CHARACTERS) {
      return scanner.accept(reader, rootNode, ctx, instance);
    }
    int counter = 0;

    while (reader.hasNext()) {
      if (reader.peek() == XMLStreamConstants.START_ELEMENT) {
        counter++;
        scanner.accept(reader, reader.peekNodeName(), ctx, instance);
      }
      if (reader.peek() == XMLStreamConstants.END_ELEMENT) {
        counter--;
        if (counter < 0) {
          break;
        }
      } else if (reader.peek() == XMLStreamConstants.END_DOCUMENT) {
        return instance;
      }
      reader.next();
    }
    return instance;
  }

  @Override
  public <T> Collection<T> iterateOverCollection(
      XMLReader reader,
      Collection<T> collection,
      Scanner<T> scanner,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params,
      boolean isWrapCollections)
      throws XMLStreamException {

    scanner.accept(reader, ctx, (T) collection);
    return collection;
  }

  @Override
  public <K, V> Map<K, V> doDeserializeMap(
      XMLReader reader,
      Map<K, V> collection,
      Function<String, XMLDeserializer<K>> keyDeserializer,
      Function<String, XMLDeserializer<V>> valueDeserializer,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    doDeserializeMap(
        reader,
        collection,
        (reader1, ctx1, instance, counter1) -> {
          K key = null;
          V value = null;
          if (reader1.peekNodeName().getLocalPart().equals("entry")) {
            reader1.next();
          }
          if (reader1.peekNodeName().getLocalPart().equals("key")) {
            key = keyDeserializer.apply(getXsiType(reader1)).deserialize(reader1, ctx1, params);
            if (reader1.peek() == XMLStreamConstants.CHARACTERS) {
              reader1.next();
            }

            if (reader1.peek() == XMLStreamConstants.END_ELEMENT) {
              reader1.next();
            }
          }
          if (reader1.peekNodeName().getLocalPart().equals("value")) {
            value = valueDeserializer.apply(getXsiType(reader1)).deserialize(reader1, ctx1, params);
            if (reader1.peek() == XMLStreamConstants.CHARACTERS) {
              reader1.next();
            }
          }
          collection.put(key, value);
        },
        ctx,
        params);
    return collection;
  }

  private <K, V> Map<K, V> doDeserializeMap(
      XMLReader reader,
      Map<K, V> collection,
      MapScanner<K, V> scanner,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params)
      throws XMLStreamException {
    AtomicInteger propertyCounter = new AtomicInteger(0);

    while (reader.hasNext()) {
      reader.next();
      switch (reader.peek()) {
        case XMLStreamConstants.START_ELEMENT:
          propertyCounter.incrementAndGet();
          if (reader.peekNodeName().getLocalPart().equals("entry")) {
            scanner.accept(reader, ctx, collection, propertyCounter);
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          propertyCounter.decrementAndGet();
          if (propertyCounter.get() < 0) {
            if (collection.isEmpty()) {
              return null;
            }
            return collection;
          }
          break;
        case XMLStreamConstants.END_DOCUMENT:
          break;
        default:
          throw new XMLStreamException();
      }
    }

    if (collection.isEmpty()) {
      return null;
    }
    return collection;
  }

  protected String getXsiType(XMLReader reader) {
    if (reader != null) {
      for (int i = 0; i < reader.getAttributeCount(); i++) {
        if (reader.getAttributeName(i).equals(xsiType)) {
          return reader.getAttributeValue(i);
        }
      }
    }
    return null;
  }

  @FunctionalInterface
  private interface MapScanner<K, V> {

    void accept(
        XMLReader reader, XMLDeserializationContext ctx, Map<K, V> instance, AtomicInteger counter)
        throws XMLStreamException;
  }
}
