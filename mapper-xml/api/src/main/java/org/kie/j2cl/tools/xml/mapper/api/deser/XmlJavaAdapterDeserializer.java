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

package org.kie.j2cl.tools.xml.mapper.api.deser;

import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

public class XmlJavaAdapterDeserializer<V, T> extends XMLDeserializer<T> {

  private final XMLDeserializer<V> internalXMLDeserializer;
  private final Function<V, T> converter;

  public XmlJavaAdapterDeserializer(
      XMLDeserializer<V> internalXMLDeserializer, Function<V, T> converter) {
    this.internalXMLDeserializer = internalXMLDeserializer;
    this.converter = converter;
  }

  @Override
  protected T doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    V result = internalXMLDeserializer.deserialize(reader, ctx, params);
    return converter.apply(result);
  }

  @Override
  public T deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    V result = internalXMLDeserializer.deserialize(value, ctx, params);
    return converter.apply(result);
  }
}
