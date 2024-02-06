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

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.lang.Void}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class VoidXMLDeserializer extends XMLDeserializer<Void> {

  private static final VoidXMLDeserializer INSTANCE = new VoidXMLDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link VoidXMLDeserializer}
   */
  public static VoidXMLDeserializer getInstance() {
    return INSTANCE;
  }

  private VoidXMLDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public Void doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    // we should never be here, the null value is already handled and it's the only possible value
    // for Void
    reader.skipValue();
    return null;
  }
}
