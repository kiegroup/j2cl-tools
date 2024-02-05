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

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

public class XmlElementWrapperDeserializer<T> extends XMLDeserializer<T> {

  private final XMLDeserializer<T> internalXMLDeserializer;
  private final String name;

  public XmlElementWrapperDeserializer(XMLDeserializer<T> internalXMLDeserializer, String name) {
    this.internalXMLDeserializer = internalXMLDeserializer;
    this.name = name;
  }

  @Override
  protected T doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    int counter = 0;
    T result = null;

    while (reader.hasNext()) {
      reader.next();
      if (reader.peek() == XMLStreamConstants.START_ELEMENT) {
        counter++;
        result = internalXMLDeserializer.deserialize(reader, ctx, params);
      }
      if (reader.peek() == XMLStreamConstants.END_ELEMENT) {
        counter--;
      }
      if (counter < 0) {
        break;
      }
    }
    return result;
  }
}
