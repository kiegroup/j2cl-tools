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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;

/**
 * Base implementation of {@link XMLSerializer} for beans.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractBeanXMLSerializer<T> extends XMLSerializer<T>
    implements InternalSerializer<T> {

  protected final BeanPropertySerializer[] serializers;

  private final TypeSerializationInfo<T> defaultTypeInfo;

  /** Constructor for AbstractBeanXMLSerializer. */
  protected AbstractBeanXMLSerializer() {
    this.serializers = initSerializers();
    this.defaultTypeInfo = initTypeInfo();
  }

  /**
   * Initialize the {@link Map} containing the property serializers. Returns an empty map if there
   * are no properties to serialize.
   *
   * @return an array of {@link BeanPropertySerializer} objects.
   */
  protected BeanPropertySerializer[] initSerializers() {
    return new BeanPropertySerializer[0];
  }

  /**
   * Initialize the {@link TypeSerializationInfo}. Returns null if there is no { XMLTypeInfo}
   * annotation on bean.
   *
   * @return a {@link TypeSerializationInfo} object.
   */
  protected TypeSerializationInfo<T> initTypeInfo() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, T value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    getSerializer(value, ctx).serializeInternally(writer, value, ctx, params, defaultTypeInfo);
  }

  private InternalSerializer<T> getSerializer(T value, XMLSerializationContext ctx) {
    if (value.getClass() == getSerializedType()) {
      return this;
    }
    if (ctx.getLogger().isLoggable(Level.FINE)) {
      ctx.getLogger()
          .fine(
              "Cannot find serializer for class "
                  + value.getClass()
                  + ". Fallback to the serializer of "
                  + getSerializedType());
    }
    return this;
  }

  /**
   * getSerializedType
   *
   * @return a {@link Class} object.
   */
  public abstract Class getSerializedType();

  protected String getSchemaLocation() {
    return null;
  }

  protected Pair<String, String> getTargetNamespace() {
    return null;
  }

  protected List<Pair<String, String>> getXmlNs() {
    return Collections.emptyList();
  }

  /** {@inheritDoc} */
  public void serializeInternally(
      XMLWriter writer,
      T value,
      XMLSerializationContext ctx,
      XMLSerializerParameters params,
      TypeSerializationInfo<T> defaultTypeInfo)
      throws XMLStreamException {
    // Processing the parameters. We fallback to default if parameter is not present.
    final TypeSerializationInfo typeInfo =
        null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();

    serializeObject(writer, value, ctx, typeInfo);
  }

  /**
   * Serializes all the properties of the bean in a json object.
   *
   * @param writer writer
   * @param value bean to serialize
   * @param ctx context of the serialization process
   */
  private void serializeObject(
      XMLWriter writer, T value, XMLSerializationContext ctx, TypeSerializationInfo typeInfo)
      throws XMLStreamException {
    serializeObject(writer, value, ctx, getSerializeObjectName(), typeInfo);
  }

  /**
   * Serializes all the properties of the bean in a json object.
   *
   * @param writer writer
   * @param value bean to serialize
   * @param ctx context of the serialization process
   * @param typeName in case of type info as property, the name of the property
   * @param typeInformation in case of type info as property, the type information
   */
  protected void serializeObject(
      XMLWriter writer,
      T value,
      XMLSerializationContext ctx,
      String typeName,
      TypeSerializationInfo typeInformation)
      throws XMLStreamException {
    if (value == null && !ctx.isSerializeNulls()) {
      return;
    }
    String prefix = getPrefix(getNamespace());
    boolean addNamespace = getNamespace() != null ? lookupNamespace(getNamespace()) : false;

    if (prefix != null && (addNamespace || getNamespace() != null)) {
      writer.beginObject(prefix, getNamespace(), typeName);
    } else if (addNamespace && getNamespace() != null && parent != null) {
      writer.beginObject(getNamespace(), typeName);
    } else {
      writer.beginObject(typeName);
    }
    writeNamespace(writer, prefix);
    setXsiType(writer, ctx);
    serializeAttribute(writer, value, ctx);
    serializeProperties(writer, value, ctx);
    writer.endObject();
  }

  protected String getXmlValuePropertyName() {
    return null;
  }

  private void setXsiType(XMLWriter writer, XMLSerializationContext ctx) throws XMLStreamException {
    for (String s : xsiType) {
      writer.writeAttribute("xsi:type", s);
    }
  }

  private void serializeAttribute(XMLWriter writer, T value, XMLSerializationContext ctx)
      throws XMLStreamException {
    for (BeanPropertySerializer<T, ?> propertySerializer : serializers) {
      if (propertySerializer.isAttribute()) {
        propertySerializer.setParent(this).serialize(writer, value, ctx);
      }
    }
  }

  private void serializeProperties(XMLWriter writer, T value, XMLSerializationContext ctx)
      throws XMLStreamException {

    for (BeanPropertySerializer<T, ?> propertySerializer : serializers) {
      if (!propertySerializer.isAttribute()) {
        if (propertySerializer.getValue(value, ctx) == null && !ctx.isSerializeNulls()) {
          continue;
        }
        if (propertySerializer.propertyName.equals(getXmlValuePropertyName())) {
          propertySerializer
              .setParent(this)
              .setPropertyType(PropertyType.XML_VALUE)
              .serialize(writer, value, ctx);
        } else {
          propertySerializer.setParent(this).serialize(writer, value, ctx);
        }
      }
    }
  }

  private String getSerializeObjectName() {
    if (propertyName != null) {
      return propertyName;
    } else if (getXmlRootElement() != null) {
      return getXmlRootElement();
    } else {
      return getSerializedType().getSimpleName();
    }
  }

  protected String getXmlRootElement() {
    return null;
  }
}
