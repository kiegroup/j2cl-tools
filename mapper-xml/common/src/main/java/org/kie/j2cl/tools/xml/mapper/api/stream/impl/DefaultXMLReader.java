/*
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
// @formatter:off
/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.j2cl.tools.xml.mapper.api.stream.impl;

import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.GwtIncompatible;
import org.kie.j2cl.tools.xml.mapper.api.utils.NumberUtils;

public class DefaultXMLReader extends JsNativeXMLReader {

  @GwtIncompatible private final javax.xml.stream.XMLStreamReader reader;

  private String input;

  @GwtIncompatible
  private final com.ctc.wstx.stax.WstxInputFactory xmlInputFactory =
      new com.ctc.wstx.stax.WstxInputFactory();

  /**
   * Creates a new instance that reads a JSON-encoded stream from {@code in}.
   *
   * @param in a {@link String} object.
   */
  @GwtIncompatible
  public DefaultXMLReader(String in) throws XMLStreamException {

    xmlInputFactory.setProperty(org.codehaus.stax2.XMLInputFactory2.SUPPORT_DTD, false);

    if (in == null) {
      throw new NullPointerException("in == null");
    }
    this.input = in.replaceAll("(?!>\\s+</)(>\\s+<)", "><");

    if (xmlInputFactory == null) {
      throw new NullPointerException("xmlInputFactory == null");
    }

    InputStream byteArrayInputStream = new java.io.ByteArrayInputStream(input.getBytes());
    reader = xmlInputFactory.createXMLStreamReader(byteArrayInputStream);
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public boolean hasNext() throws XMLStreamException {
    try {
      return reader.hasNext();
    } catch (javax.xml.stream.XMLStreamException e) {
      throw new XMLStreamException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public int peek() {
    return reader.getEventType();
  }

  @Override
  @GwtIncompatible
  public QName peekNodeName() throws XMLStreamException {
    return reader.getName();
  }

  @Override
  @GwtIncompatible
  public String rowValue() throws XMLStreamException {
    return reader.getText();
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public String nextString() throws XMLStreamException {
    if (peek() == 1) {
      reader.next();
    }

    if (peek() == 2) {
      return null;
    }
    return reader.getText();
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public boolean nextBoolean() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return false;
    }
    return Boolean.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public double nextDouble() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Double.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public long nextLong() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Long.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public int nextInt() throws XMLStreamException {
    String value = nextString();
    if (value == null) {
      return 0;
    }
    return Integer.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public void close() throws XMLStreamException {
    reader.close();
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public void skipValue() throws XMLStreamException {
    reader.next();
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public String nextValue() {
    return reader.getText();
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public String getInput() throws XMLStreamException {
    return input;
  }

  /** {@inheritDoc} */
  @Override
  @GwtIncompatible
  public Number nextNumber() throws XMLStreamException {
    return NumberUtils.toNumber(nextString());
  }

  @Override
  @GwtIncompatible
  public void next() throws XMLStreamException {
    reader.next();
  }

  @Override
  @GwtIncompatible
  public int getAttributeCount() {
    return reader.getAttributeCount();
  }

  @Override
  @GwtIncompatible
  public QName getAttributeName(int index) {
    return reader.getAttributeName(index);
  }

  @Override
  @GwtIncompatible
  public String getAttributeValue(int index) {
    return reader.getAttributeValue(index);
  }

  @Override
  @GwtIncompatible
  public String getAttributeType(int index) {
    return reader.getAttributeType(index);
  }
}
