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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

/**
 * XMLReader interface.
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public interface XMLReader {

  /**
   * Returns true if the current array or object has another element.
   *
   * @return a boolean.
   */
  boolean hasNext() throws XMLStreamException;

  /**
   * Returns the type of the next token without consuming it.
   *
   * @return a {@link XMLStreamConstants} object.
   */
  int peek() throws XMLStreamException;

  /**
   * Returns the text value of the current token.
   *
   * @return a {@link String} object.
   */
  QName peekNodeName() throws XMLStreamException;

  String rowValue() throws XMLStreamException;

  /**
   * Returns the {@link String } value of the next token, consuming it. If the next token is a
   * number, this method will return its string form.
   *
   * @return a {@link String} object.
   * @throws IllegalStateException if the next token is not a string or if this reader is closed.
   */
  String nextString() throws XMLStreamException;

  /**
   * Returns the {@link boolean} value of the next token, consuming it.
   *
   * @return a boolean.
   * @throws IllegalStateException if the next token is not a boolean or if this reader is closed.
   */
  boolean nextBoolean() throws XMLStreamException;

  /**
   * Returns the {@link double} value of the next token, consuming it. If the next token is a
   * string, this method will attempt to parse it as a double using {@link
   * Double#parseDouble(String)}.
   *
   * @return a double.
   * @throws IllegalStateException if the next token is not a literal value.
   * @throws NumberFormatException if the next literal value cannot be parsed as a double, or is
   *     non-finite.
   */
  double nextDouble() throws XMLStreamException;

  /**
   * Returns the {@link long} value of the next token, consuming it. If the next token is a string,
   * this method will attempt to parse it as a long. If the next token's numeric value cannot be
   * exactly represented by a Java {@code long}, this method throws.
   *
   * @return a long.
   * @throws IllegalStateException if the next token is not a literal value.
   * @throws NumberFormatException if the next literal value cannot be parsed as a number, or
   *     exactly represented as a long.
   */
  long nextLong() throws XMLStreamException;

  /**
   * Returns the {@link int} value of the next token, consuming it. If the next token is a string,
   * this method will attempt to parse it as an int. If the next token's numeric value cannot be
   * exactly represented by a Java {@code int}, this method throws.
   *
   * @return a int.
   * @throws IllegalStateException if the next token is not a literal value.
   * @throws NumberFormatException if the next literal value cannot be parsed as a number, or
   *     exactly represented as an int.
   */
  int nextInt() throws XMLStreamException;

  /** Closes this JSON reader and the underlying {@link java.io.Reader}. */
  void close() throws XMLStreamException;

  /**
   * Skips the next value recursively. If it is an object or array, all nested elements are skipped.
   * This method is intended for use when the JSON token stream contains unrecognized or unhandled
   * values.
   */
  void skipValue() throws XMLStreamException;

  /**
   * Reads the next value recursively and returns it as a String. If it is an object or array, all
   * nested elements are read.
   *
   * @return a {@link String} object.
   */
  String nextValue() throws XMLStreamException;

  /**
   * Returns the {@link Number} value of the next token, consuming it. This method will attempt to
   * return the best matching number. For non-decimal number, if it fits into an int, an int is
   * returned, else a long else a {@link java.math.BigInteger}. For decimal number, a double is
   * returned. If the next token's numeric value cannot be exactly represented by a Java {@link
   * Number}, this method throws.
   *
   * @return a {@link Number} object.
   * @throws IllegalStateException if the next token is not a number.
   * @throws NumberFormatException if the next value cannot be parsed as a number.
   */
  Number nextNumber() throws XMLStreamException;

  void next() throws XMLStreamException;

  String getInput() throws XMLStreamException;

  int getAttributeCount();

  QName getAttributeName(int index);

  String getAttributeValue(int index);

  String getAttributeType(int index);
}
