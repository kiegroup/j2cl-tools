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

package org.kie.j2cl.tools.xml.mapper.api.deser.array;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.StringXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;
import org.kie.j2cl.tools.xml.mapper.api.utils.Base64Utils;

/**
 * Default {@link XMLDeserializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveByteArrayXMLDeserializer extends AbstractArrayXMLDeserializer<byte[]> {

  private PrimitiveByteArrayXMLDeserializer() {}

  /**
   * getInstance
   *
   * @return an instance of {@link PrimitiveByteArrayXMLDeserializer}
   */
  public static PrimitiveByteArrayXMLDeserializer getInstance() {
    return new PrimitiveByteArrayXMLDeserializer();
  }

  /** {@inheritDoc} */
  @Override
  public byte[] doDeserializeArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {

    String result = StringXMLDeserializer.getInstance().doDeserialize(reader, ctx, params);
    if (result != null) {
      return Base64Utils.fromBase64(result);
    }

    return new byte[0];
  }

  /** {@inheritDoc} */
  @Override
  protected byte[] doDeserializeSingleArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    return new byte[] {
      BaseNumberXMLDeserializer.ByteXMLDeserializer.getInstance().deserialize(reader, ctx, params)
    };
  }
}
