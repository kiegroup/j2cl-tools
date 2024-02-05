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

import java.util.function.Function;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.lang.Enum}.
 *
 * @param <E> Type of the enum
 * @author Nicolas Morel
 * @version $Id: $
 */
public class EnumXMLDeserializer<E extends Enum<E>> extends XMLDeserializer<E> {

  private Function<String, E> func;

  private Class<E> enumClass;

  /**
   * Constructor for EnumXMLDeserializer.
   *
   * @param func get Enum by their name
   */
  protected EnumXMLDeserializer(Class<E> enumClass, Function<String, E> func) {
    this.func = func;
    this.enumClass = enumClass;
  }

  /**
   * newInstance
   *
   * @param enumClass
   * @param func get Enum by their name
   * @return a new instance of {@link EnumXMLDeserializer}
   */
  public static <E extends Enum<E>> EnumXMLDeserializer<E> newInstance(
      Class<E> enumClass, Function<String, E> func) {
    return new EnumXMLDeserializer<>(enumClass, func);
  }

  @Override
  public E deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    try {
      return getEnum(value);
    } catch (IllegalArgumentException ex) {
      if (ctx.isReadUnknownEnumValuesAsNull()) {
        return null;
      }
      throw ex;
    }
  }

  /** {@inheritDoc} */
  @Override
  public E doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    try {
      return getEnum(reader.nextString());
    } catch (IllegalArgumentException ex) {
      if (ctx.isReadUnknownEnumValuesAsNull()) {
        return null;
      }
      throw ex;
    }
  }

  public <E extends Enum<E>> E getEnum(String name) {
    E result = (E) func.apply(name);
    if (result != null) {
      return result;
    }
    throw new IllegalArgumentException(
        "[" + name + "] is not a valid enum constant for Enum type " + getEnumClass().getName());
  }

  /**
   * Getter for the field <code>enumClass</code>.
   *
   * @return a {@link java.lang.Class} object.
   */
  public Class<E> getEnumClass() {
    return enumClass;
  }
}
