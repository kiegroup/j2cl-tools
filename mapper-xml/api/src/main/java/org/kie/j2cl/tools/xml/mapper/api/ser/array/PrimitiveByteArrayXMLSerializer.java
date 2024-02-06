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

package org.kie.j2cl.tools.xml.mapper.api.ser.array;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;
import org.kie.j2cl.tools.xml.mapper.api.utils.Base64Utils;

/**
 * Default {@link XMLSerializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 * @version $Id: $Id
 */
public class PrimitiveByteArrayXMLSerializer extends BasicArrayXMLSerializer<byte[]> {

  private PrimitiveByteArrayXMLSerializer() {}

  /**
   * getInstance.
   *
   * @return an instance of {@link PrimitiveByteArrayXMLSerializer}
   */
  public static BasicArrayXMLSerializer getInstance(String propertyName) {
    return new PrimitiveByteArrayXMLSerializer().setPropertyName(propertyName);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(byte[] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, byte[] values, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (!ctx.isWriteEmptyXMLArrays() && values.length == 0) {
      writer.nullValue();
      return;
    }
    writer.value(Base64Utils.toBase64(values));
  }
}
