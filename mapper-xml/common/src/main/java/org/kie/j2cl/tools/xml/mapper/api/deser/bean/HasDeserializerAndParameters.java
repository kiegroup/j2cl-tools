/*
 * Copyright 2014 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.deser.bean;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.MapperContextProvider;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Lazy initialize a {@link XMLDeserializer}
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class HasDeserializerAndParameters<V, S extends XMLDeserializer<V>>
    extends HasDeserializer<V, S> {

  private XMLDeserializerParameters parameters;

  /**
   * Deserializes the property defined for this instance.
   *
   * @param reader reader
   * @param ctx context of the deserialization process
   * @return a V object.
   */
  public V deserialize(XMLReader reader, XMLDeserializationContext ctx) throws XMLStreamException {
    return getDeserializer(reader).deserialize(reader, ctx, getParameters());
  }

  /**
   * Getter for the field <code>parameters</code>.
   *
   * @return a {@link XMLDeserializerParameters} object.
   */
  protected XMLDeserializerParameters getParameters() {
    if (null == parameters) {
      parameters = newParameters();
    }
    return parameters;
  }

  /**
   * newParameters
   *
   * @return a {@link XMLDeserializerParameters} object.
   */
  protected XMLDeserializerParameters newParameters() {
    return MapperContextProvider.get().defaultDeserializerParameters();
  }

  public V deserialize(String value, XMLDeserializationContext ctx) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return getDeserializer(null).deserialize(value, ctx, getParameters());
  }
}
