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

package org.kie.j2cl.tools.xml.mapper.api.stream;

import javax.xml.stream.XMLStreamException;

/**
 * XMLWriter interface.
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public interface XMLWriter {

  /**
   * getSerializeNulls
   *
   * @return a boolean.
   */
  boolean getSerializeNulls();

  /**
   * Sets whether object members are serialized when their value is null. This has no impact on
   * array elements. The default is true.
   *
   * @param serializeNulls a boolean.
   */
  void setSerializeNulls(boolean serializeNulls);

  /**
   * Begins encoding a new array. Each call to this method must be paired with a call to {@link
   * #endArray}.
   *
   * @return this writer.
   */
  XMLWriter beginArray() throws XMLStreamException;

  /**
   * Ends encoding the current array.
   *
   * @return this writer.
   */
  XMLWriter endArray() throws XMLStreamException;

  /**
   * Begins encoding a new object. Each call to this method must be paired with a call to {@link
   * #endObject}.
   *
   * @return this writer.
   */
  XMLWriter beginObject(String name) throws XMLStreamException;

  XMLWriter beginObject(String namespace, String name) throws XMLStreamException;

  XMLWriter beginObject(String prefix, String namespace, String name) throws XMLStreamException;

  /**
   * Ends encoding the current object.
   *
   * @return this writer.
   */
  XMLWriter endObject() throws XMLStreamException;

  /**
   * Encodes the property name.
   *
   * @param name the name of the forthcoming value. May not be null.
   * @return this writer.
   */
  XMLWriter name(String name) throws XMLStreamException;

  /**
   * Encodes the property name without escaping it.
   *
   * @param name the name of the forthcoming value. May not be null.
   * @return this writer.
   */
  XMLWriter unescapeName(String name) throws XMLStreamException;

  /**
   * Encodes {@code value}.
   *
   * @param value the literal string value, or null to encode a null literal.
   * @return this writer.
   */
  XMLWriter value(String value) throws XMLStreamException;

  /**
   * Encodes {@code value} without escaping it.
   *
   * @param value the literal string value, or null to encode a null literal.
   * @return this writer.
   */
  XMLWriter unescapeValue(String value) throws XMLStreamException;

  /**
   * Encodes {@code null}.
   *
   * @return this writer.
   */
  XMLWriter nullValue() throws XMLStreamException;

  /**
   * Encodes {@code value}.
   *
   * @param value a boolean.
   * @return this writer.
   */
  XMLWriter value(boolean value) throws XMLStreamException;

  /**
   * Encodes {@code value}.
   *
   * @param value a finite value. May not be {@link java.lang.Double#isNaN() NaNs} or {@link
   *     java.lang.Double#isInfinite() infinities}.
   * @return this writer.
   */
  XMLWriter value(double value) throws XMLStreamException;

  /**
   * Encodes {@code value}.
   *
   * @param value a long.
   * @return this writer.
   */
  XMLWriter value(long value) throws XMLStreamException;

  /**
   * Encodes {@code value}.
   *
   * @param value a finite value. May not be {@link java.lang.Double#isNaN() NaNs} or {@link
   *     java.lang.Double#isInfinite() infinities}.
   * @return this writer.
   */
  XMLWriter value(Number value) throws XMLStreamException;

  /**
   * Ensures all buffered data is written to the underlying {@link java.lang.StringBuilder} and
   * flushes that writer.
   */
  void flush() throws XMLStreamException;

  /** Flushes and closes this writer and the underlying {@link java.lang.StringBuilder}. */
  void close() throws XMLStreamException;

  /**
   * getOutput
   *
   * @return the output when the serialization is over
   */
  String getOutput();

  void writeDefaultNamespace(String namespace) throws XMLStreamException;

  void writeNamespace(String prefix, String namespace) throws XMLStreamException;

  void endNs();

  void writeCData(String value) throws XMLStreamException;

  void writeCharacters(String value) throws XMLStreamException;

  void writeAttribute(String propertyName, String value) throws XMLStreamException;

  void writeSchemaLocation(String s, String schemaLocation) throws XMLStreamException;

  void writeTargetNamespace(String targetNamespace) throws XMLStreamException;
}
