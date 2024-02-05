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

package org.kie.j2cl.tools.xml.mapper.api.ser;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for {@link String}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringXMLSerializer extends XMLSerializer<String> {

  private StringXMLSerializer() {}

  /**
   * getInstance
   *
   * @return an instance of {@link StringXMLSerializer}
   */
  public static StringXMLSerializer getInstance() {
    return new StringXMLSerializer();
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, String value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (ctx.isSerializeNulls() || !isEmpty(value)) {
      if (type.equals(PropertyType.CDATA)) {
        beginObject(writer);
        writer.writeCData(value);
        writer.endObject();
      } else if (type.equals(PropertyType.CDATA_INLINE)) {
        writer.writeCData(value);
      } else if (isAttribute) {
        writeAttribute(writer, value);
      } else {
        writeValue(writer, value);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(String value) {
    return null == value || value.length() == 0;
  }
}
