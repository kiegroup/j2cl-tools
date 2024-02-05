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

package org.kie.j2cl.tools.xml.mapper.api.deser;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.lang.String}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringXMLDeserializer extends XMLDeserializer<String> {

  private static final StringXMLDeserializer INSTANCE = new StringXMLDeserializer();

  private PropertyType propertyType = PropertyType.COMMON;

  private StringXMLDeserializer() {}

  /**
   * getInstance
   *
   * @return an instance of {@link StringXMLDeserializer}
   */
  public static StringXMLDeserializer getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public String doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    if (propertyType.equals(PropertyType.CDATA) || propertyType.equals(PropertyType.CDATA_INLINE)) {
      this.propertyType = PropertyType.COMMON;
      reader.next();
      if (reader.peek() == XMLStreamConstants.END_ELEMENT) {
        return null;
      }
      return reader.nextValue();
    }

    return reader.nextString();
  }

  @Override
  public String deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    return value;
  }

  public StringXMLDeserializer setPropertyType(PropertyType propertyType) {
    this.propertyType = propertyType;
    return this;
  }
}
