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
package org.kie.j2cl.tools.xml.mapper.api.ser;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

public class XmlElementWrapperSerializer<T> extends XMLSerializer<T> {

  private final XMLSerializer<T> internalXMLSerializer;
  private final String name;
  private String defaultNamespace;

  public XmlElementWrapperSerializer(XMLSerializer<T> internalXMLSerializer, String name) {
    this.internalXMLSerializer = internalXMLSerializer;
    this.name = name;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, T value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (defaultNamespace != null) {
      String prefix = getPrefix(defaultNamespace);
      if (prefix != null) {
        writer.beginObject(prefix, defaultNamespace, name);
      } else writer.beginObject(defaultNamespace, name);
    } else {
      writer.beginObject(name);
    }
    internalXMLSerializer.setParent(parent).serialize(writer, value, ctx, params);
    writer.endObject();
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(T value) {
    return null == value;
  }

  public XmlElementWrapperSerializer<T> setDefaultNamespace(String n) {
    defaultNamespace = n;
    return this;
  }
}
