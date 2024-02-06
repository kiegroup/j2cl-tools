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

package org.kie.j2cl.tools.xml.mapper.api.ser.bean;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.MapperContextProvider;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BeanPropertySerializer<T, V> extends HasSerializer<V, XMLSerializer<V>> {

  protected String propertyName;
  private XMLSerializerParameters parameters = newParameters();
  private XMLSerializer parent;
  private PropertyType type = PropertyType.COMMON;

  /**
   * Constructor for BeanPropertySerializer.
   *
   * @param propertyName a {@link String} object.
   */
  protected BeanPropertySerializer(String propertyName) {
    this.propertyName = propertyName;
  }

  protected BeanPropertySerializer(String propertyName, PropertyType type) {
    this.propertyName = propertyName;
    this.type = type;
  }

  /**
   * newParameters
   *
   * @return a {@link XMLSerializerParameters} object.
   */
  protected XMLSerializerParameters newParameters() {
    return MapperContextProvider.get().defaultSerializerParameters();
  }

  /**
   * Getter for the field <code>propertyName</code>.
   *
   * @return a {@link String} object.
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Serializes the property defined for this instance.
   *
   * @param writer writer
   * @param bean bean containing the property to serialize
   * @param ctx context of the serialization process
   */
  public void serialize(XMLWriter writer, T bean, XMLSerializationContext ctx)
      throws XMLStreamException {
    writer.unescapeName(propertyName);
    getSerializer(getValue(bean, ctx) != null ? getValue(bean, ctx).getClass() : null)
        .setPropertyName(propertyName)
        .setPropertyType(type)
        .setNamespace(getNamespace())
        .setPrefix(getPrefix())
        .setParent(parent)
        .isAttribute(isAttribute())
        .serialize(writer, getValue(bean, ctx), ctx, getParameters());
  }

  /**
   * getValue
   *
   * @param bean bean containing the property to serialize
   * @param ctx context of the serialization process
   * @return the property's value
   */
  public abstract V getValue(T bean, XMLSerializationContext ctx);

  protected String getNamespace() {
    return null;
  }

  protected String getPrefix() {
    return null;
  }

  protected boolean isAttribute() {
    return false;
  }

  /**
   * Getter for the field <code>parameters</code>.
   *
   * @return a {@link XMLSerializerParameters} object.
   */
  protected XMLSerializerParameters getParameters() {
    return parameters;
  }

  BeanPropertySerializer<T, V> setParent(XMLSerializer parent) {
    this.parent = parent;
    return this;
  }

  public BeanPropertySerializer setPropertyType(PropertyType type) {
    this.type = type;
    return this;
  }
}
