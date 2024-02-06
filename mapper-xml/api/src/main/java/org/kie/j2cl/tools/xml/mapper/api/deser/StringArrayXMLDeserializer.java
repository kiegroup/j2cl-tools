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

import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.AbstractArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for array of {@link java.lang.String}.
 *
 * <p>Not working in production mode, cast problem. Can maybe work with disableCastChecking
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringArrayXMLDeserializer extends AbstractArrayXMLDeserializer<String[]> {

  private static final StringArrayXMLDeserializer INSTANCE = new StringArrayXMLDeserializer();

  private StringArrayXMLDeserializer() {}

  /**
   * getInstance
   *
   * @return an instance of {@link StringArrayXMLDeserializer}
   */
  public static StringArrayXMLDeserializer getInstance() {
    return new StringArrayXMLDeserializer();
  }

  /** {@inheritDoc} */
  @Override
  public String[] doDeserializeArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<String> list =
        deserializeIntoList(reader, ctx, s -> StringXMLDeserializer.getInstance(), params);
    return list.toArray(new String[list.size()]);
  }

  /** {@inheritDoc} */
  @Override
  protected String[] doDeserializeSingleArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    return new String[] {reader.nextString()};
  }
}
