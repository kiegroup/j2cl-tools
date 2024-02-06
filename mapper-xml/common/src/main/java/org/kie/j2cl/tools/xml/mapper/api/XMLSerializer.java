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

package org.kie.j2cl.tools.xml.mapper.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLSerializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;

/**
 * Base class for all the serializer. It handles null values and exceptions. The rest is delegated
 * to implementations.
 *
 * @author Nicolas Morel
 * @version $Id: $Id
 */
public abstract class XMLSerializer<T> {

  protected String propertyName;

  protected PropertyType type = PropertyType.COMMON;

  protected boolean isAttribute = false;

  protected XMLSerializer parent;

  protected String namespace;

  protected String prefix;

  protected List<String> xsiType = new ArrayList<>();

  protected Inheritance inheritanceType = Inheritance.NONE;

  protected Map<String, String> namespaces = new LinkedHashMap<>();

  public XMLSerializer<T> setPropertyName(String propertyName) {
    if (!inheritanceType.equals(Inheritance.TAG)) {
      this.propertyName = propertyName;
    }
    return this;
  }

  public XMLSerializer<T> setPropertyType(PropertyType type) {
    this.type = type;
    return this;
  }

  public XMLSerializer<T> isAttribute(boolean isAttribute) {
    this.isAttribute = isAttribute;
    return this;
  }

  public XMLSerializer<T> setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public XMLSerializer setParent(XMLSerializer parent) {
    this.parent = parent;
    return this;
  }

  /**
   * Serializes an object into XML output.
   *
   * @param writer {@link XMLWriter} used to write the serialized XML
   * @param value Object to serialize
   * @param ctx Context for the full serialization process
   * @throws XMLSerializationException if an error occurs during the serialization
   */
  public void serialize(XMLWriter writer, T value, XMLSerializationContext ctx)
      throws XMLSerializationException, XMLStreamException {
    serialize(writer, value, ctx, ctx.defaultParameters());
  }

  /**
   * Serializes an object into XML output.
   *
   * @param writer {@link XMLWriter} used to write the serialized XML
   * @param value Object to serialize
   * @param ctx Context for the full serialization process
   * @param params Parameters for this serialization
   * @throws XMLSerializationException if an error occurs during the serialization
   */
  public void serialize(
      XMLWriter writer, T value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLSerializationException, XMLStreamException {
    serialize(writer, value, ctx, params, false);
  }

  /**
   * Serializes an object into XML output.
   *
   * @param writer {@link XMLWriter} used to write the serialized XML
   * @param value Object to serialize
   * @param ctx Context for the full serialization process
   * @param params Parameters for this serialization
   * @param isMapValue indicate if you're serializing a Map value
   * @throws XMLSerializationException if an error occurs during the serialization
   */
  public void serialize(
      XMLWriter writer,
      T value,
      XMLSerializationContext ctx,
      XMLSerializerParameters params,
      boolean isMapValue)
      throws XMLSerializationException, XMLStreamException {
    if (null == value) {
      if (ctx.isSerializeNulls() || (isMapValue && ctx.isWriteNullMapValues())) {
        serializeNullValue(writer, ctx, params);
      }
    } else {
      doSerialize(writer, value, ctx, params);
    }
  }

  /**
   * Serialize the null value. This method allows children to override the default behaviour.
   *
   * @param writer {@link XMLWriter} used to write the serialized XML
   * @param ctx Context for the full serialization process
   * @param params Parameters for this serialization
   */
  protected void serializeNullValue(
      XMLWriter writer, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (isAttribute) {
      writer.writeAttribute(propertyName, "");
    } else {
      writer.nullValue();
    }
  }

  /**
   * Serializes a non-null object into XML output.
   *
   * @param writer {@link XMLWriter} used to write the serialized XML
   * @param value Object to serialize
   * @param ctx Context for the full serialization process
   * @param params Parameters for this serialization
   */
  protected abstract void doSerialize(
      XMLWriter writer, T value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException;

  protected void writeNamespace(XMLWriter writer, String prefix) throws XMLStreamException {
    Optional<String> defaultNamespace = getDefaultNamespace();
    if (prefix != null && defaultNamespace.isPresent()) {
      writer.writeDefaultNamespace(defaultNamespace.get());
    } else if (getNamespace() != null) {
      writer.writeDefaultNamespace(getNamespace());
    }

    if (!namespaces.isEmpty()) {
      for (Map.Entry<String, String> entry : namespaces.entrySet()) {
        if (!entry.getKey().equals("") && lookupNamespace(entry.getKey(), entry.getValue())) {
          writer.writeNamespace(entry.getKey(), entry.getValue());
        }
      }
    }

    writeSchemaLocation(writer);
    writeTargetNamespace(writer);
    writer.endNs();
  }

  private Optional<String> getDefaultNamespace() {
    if (!namespaces.isEmpty()) {
      return namespaces.entrySet().stream()
          .filter(entry -> entry.getKey().equals(""))
          .findFirst()
          .map(pair -> pair.getValue());
    }
    return Optional.empty();
  }

  public String getNamespace() {
    return null;
  }

  public XMLSerializer<T> setNamespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  private void writeSchemaLocation(XMLWriter writer) throws XMLStreamException {
    if (getSchemaLocation() != null) {
      writer.writeSchemaLocation("xsi:schemaLocation", getSchemaLocation());
    }
  }

  private void writeTargetNamespace(XMLWriter writer) throws XMLStreamException {
    if (getTargetNamespace() != null) {
      writer.writeTargetNamespace(getTargetNamespace().value);
    }
  }

  protected String getSchemaLocation() {
    return null;
  }

  protected Pair<String, String> getTargetNamespace() {
    return null;
  }

  public XMLSerializer<T> addNamespace(String prefix, String namespace) {
    namespaces.put(prefix, namespace);
    return this;
  }

  protected String getPrefix(String namespace) {
    String prefix = null;
    if (parent != null) {
      prefix = parent.getPrefix(namespace);
    }
    if (prefix != null) {
      return prefix;
    }

    if (namespace != null && !namespaces.isEmpty()) {
      for (Map.Entry<String, String> entry : namespaces.entrySet()) {
        if (!entry.getKey().equals("") && entry.getValue().equals(namespace)) {
          prefix = entry.getKey();
        }
      }
    }
    return prefix;
  }

  private boolean _lookupNamespace(String namespace) {
    boolean result = false;
    if (parent != null) {
      result = parent._lookupNamespace(namespace);
    }
    if (result) {
      return true;
    } else {
      return namespace.equals(getNamespace());
    }
  }

  protected boolean lookupNamespace(String namespace) {
    if (parent == null) {
      return true;
    }
    return !parent._lookupNamespace(namespace);
  }

  protected boolean lookupNamespace(String prefix, String namespace) {
    if (parent == null) {
      return true;
    }
    return !parent._lookupNamespace(prefix, namespace);
  }

  private boolean _lookupNamespace(String prefix, String namespace) {
    boolean result = false;
    if (parent != null) {
      result = parent._lookupNamespace(prefix, namespace);
    }
    if (result) {
      return true;
    } else {
      return namespaces.containsKey(prefix) && namespaces.get(prefix).equals(namespace);
    }
  }

  protected String getXmlRootElement() {
    return null;
  }

  public XMLSerializer<T> setType(String value, Inheritance type) {
    if (Inheritance.XSI.equals(type)) {
      xsiType.add(value);
    } else if (Inheritance.TAG.equals(type)) {
      propertyName = value;
    }
    inheritanceType = type;
    return this;
  }

  protected void writeAttribute(XMLWriter writer, String value) throws XMLStreamException {
    if (namespace != null && !namespace.equals(parent.getNamespace())) {
      String prefix = getPrefix(namespace);
      if (prefix == null) {
        throw new XMLStreamException(
            "Unable to process property "
                + propertyName
                + " with namespace "
                + namespace
                + ", set namespace prefix in @XmlSchema");
      }
      writer.writeAttribute(prefix + ":" + propertyName, value);
    } else {
      writer.writeAttribute(propertyName, value);
    }
  }

  protected void beginObject(XMLWriter writer) throws XMLStreamException {
    if (type.equals(PropertyType.XML_VALUE)) {
      return;
    }
    if (namespace != null) {
      String prefix = getPrefix(namespace);
      if (prefix != null) {
        writer.beginObject(prefix, namespace, propertyName);
      } else {
        writer.beginObject(namespace, propertyName);
      }
    } else {
      writer.beginObject(propertyName);
    }
  }

  protected void writeValue(XMLWriter writer, String value) throws XMLStreamException {
    if (type.equals(PropertyType.XML_VALUE)) {
      writer.writeCharacters(value);
      return;
    }
    if (namespace != null) {
      String prefix = getPrefix(namespace);
      if (prefix == null) {
        writer.beginObject(namespace, propertyName);
      } else {
        writer.beginObject(prefix, namespace, propertyName);
      }
      writer.writeCharacters(value);
      writer.endObject();
    } else {
      writer.value(value);
    }
  }

  /**
   * isEmpty.
   *
   * @param value the value
   * @return true if the value is empty
   */
  protected boolean isEmpty(T value) {
    return null == value;
  }
}
