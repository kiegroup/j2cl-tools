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
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.lang.Character}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class CharacterXMLDeserializer extends XMLDeserializer<Character> {

  private static final CharacterXMLDeserializer INSTANCE = new CharacterXMLDeserializer();

  private CharacterXMLDeserializer() {}

  /**
   * getInstance
   *
   * @return an instance of {@link CharacterXMLDeserializer}
   */
  public static CharacterXMLDeserializer getInstance() {
    return INSTANCE;
  }

  @Override
  public Character deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    if (value.isEmpty()) {
      return null;
    }
    return value.charAt(0);
  }

  /** {@inheritDoc} */
  @Override
  public Character doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    String value = reader.nextString();
    if (value == null || value.isEmpty()) {
      return null;
    }
    return value.charAt(0);
  }
}
