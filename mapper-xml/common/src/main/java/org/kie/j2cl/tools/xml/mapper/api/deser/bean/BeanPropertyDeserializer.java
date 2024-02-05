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

package org.kie.j2cl.tools.xml.mapper.api.deser.bean;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Deserializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BeanPropertyDeserializer<T, V>
    extends HasDeserializerAndParameters<V, XMLDeserializer<V>> {

  private boolean cdata = false;

  public BeanPropertyDeserializer() {}

  public BeanPropertyDeserializer(boolean cdata) {
    this.cdata = cdata;
  }

  /**
   * Deserializes the property defined for this instance.
   *
   * @param reader reader
   * @param bean bean to set the deserialized property to
   * @param ctx context of the deserialization process
   */
  public void deserialize(XMLReader reader, T bean, XMLDeserializationContext ctx)
      throws XMLStreamException {
    V value = deserialize(reader, ctx);
    if (value != null) {
      setValue(bean, value, ctx);
    }
  }

  /**
   * setValue
   *
   * @param bean a T object.
   * @param value a V object.
   * @param ctx a {@link XMLDeserializationContext} object.
   */
  public abstract void setValue(T bean, V value, XMLDeserializationContext ctx);

  public void deserialize(String value, T bean, XMLDeserializationContext ctx) {
    setValue(bean, deserialize(value, ctx), ctx);
  }

  protected boolean isAttribute() {
    return false;
  }
}
