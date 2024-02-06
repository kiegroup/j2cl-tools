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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLIterator;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;
import org.kie.j2cl.tools.xml.mapper.api.stream.impl.DefaultXMLIterator;
import org.kie.j2cl.tools.xml.mapper.api.stream.impl.DefaultXMLReader;
import org.kie.j2cl.tools.xml.mapper.api.stream.impl.JsNativeXMLReader;

/**
 * Context for the deserialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class DefaultXMLDeserializationContext implements XMLDeserializationContext {

  private static final Logger logger = Logger.getLogger("XMLDeserialization");
  /*
   * Deserialization options
   */
  private final boolean failOnUnknownProperties;
  private final boolean acceptSingleValueAsArray;
  private final boolean wrapExceptions;
  private final boolean useSafeEval;
  private final boolean readUnknownEnumValuesAsNull;
  private final boolean useBrowserTimezone;
  private final XMLIterator iterator;
  private final boolean readDateAsTimestamps;

  private DefaultXMLDeserializationContext(
      boolean failOnUnknownProperties,
      boolean acceptSingleValueAsArray,
      boolean wrapExceptions,
      boolean useSafeEval,
      boolean readUnknownEnumValuesAsNull,
      boolean useBrowserTimezone,
      boolean readDateAsTimestamps) {
    this.failOnUnknownProperties = failOnUnknownProperties;
    this.acceptSingleValueAsArray = acceptSingleValueAsArray;
    this.wrapExceptions = wrapExceptions;
    this.useSafeEval = useSafeEval;
    this.readUnknownEnumValuesAsNull = readUnknownEnumValuesAsNull;
    this.useBrowserTimezone = useBrowserTimezone;
    this.readDateAsTimestamps = readDateAsTimestamps;
    this.iterator = new DefaultXMLIterator();
  }

  /**
   * builder
   *
   * @return a {@link DefaultXMLDeserializationContext.Builder} object.
   */
  public static Builder builder() {
    return new DefaultBuilder();
  }

  /**
   * {@inheritDoc}
   *
   * <p>isFailOnUnknownProperties
   *
   * @see Builder#failOnUnknownProperties(boolean)
   */
  @Override
  public boolean isFailOnUnknownProperties() {
    return failOnUnknownProperties;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isAcceptSingleValueAsArray
   *
   * @see Builder#acceptSingleValueAsArray(boolean)
   */
  @Override
  public boolean isAcceptSingleValueAsArray() {
    return acceptSingleValueAsArray;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isUseSafeEval
   *
   * @see Builder#useSafeEval(boolean)
   */
  @Override
  public boolean isUseSafeEval() {
    return useSafeEval;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isReadUnknownEnumValuesAsNull
   *
   * @see Builder#readUnknownEnumValuesAsNull(boolean)
   */
  @Override
  public boolean isReadUnknownEnumValuesAsNull() {
    return readUnknownEnumValuesAsNull;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isUseBrowserTimezone
   *
   * @see Builder#isUseBrowserTimezone()
   */
  @Override
  public boolean isUseBrowserTimezone() {
    return useBrowserTimezone;
  }

  @Override
  public boolean isReadDateAsTimestamps() {
    return readDateAsTimestamps;
  }

  /**
   * {@inheritDoc}
   *
   * <p>newXMLReader
   */
  @Override
  public XMLReader newXMLReader(String input) throws XMLStreamException {
    return new XMLReaderHolder().newXMLReader(input);
  }

  @Override
  public XMLIterator iterator() {
    return iterator;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error with current reader state and returns a corresponding exception.
   */
  @Override
  public XMLDeserializationException traceError(String message) throws XMLStreamException {
    return traceError(message, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error with current reader state and returns a corresponding exception.
   */
  @Override
  public XMLDeserializationException traceError(String message, XMLReader reader)
      throws XMLStreamException {
    getLogger().log(Level.SEVERE, message);
    traceReaderInfo(reader);
    return new XMLDeserializationException(message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error and returns a corresponding exception.
   */
  @Override
  public RuntimeException traceError(RuntimeException cause) {
    getLogger().log(Level.SEVERE, "Error during deserialization", cause);
    if (wrapExceptions) {
      return new XMLDeserializationException(cause);
    } else {
      return cause;
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error with current reader state and returns a corresponding exception.
   */
  @Override
  public RuntimeException traceError(RuntimeException cause, XMLReader reader)
      throws XMLStreamException {
    RuntimeException exception = traceError(cause);
    traceReaderInfo(reader);
    return exception;
  }

  /** {@inheritDoc} */
  @Override
  public XMLDeserializerParameters defaultParameters() {
    return MapperContextProvider.get().defaultDeserializerParameters();
  }

  /** {@inheritDoc} */
  @Override
  public Logger getLogger() {
    return logger;
  }

  /** Trace the current reader state */
  private void traceReaderInfo(XMLReader reader) throws XMLStreamException {
    if (null != reader && getLogger().isLoggable(Level.INFO)) {
      getLogger().log(Level.INFO, "Error in input <" + reader.getInput() + ">");
    }
  }

  /**
   * Builder for {@link XMLDeserializationContext}. To override default settings globally, you can
   * extend this class, modify the default settings inside the constructor and tell the compiler to
   * use your builder instead in your gwt.xml file :
   *
   * <pre>{@code
   * <replace-with class="your.package.YourBuilder">
   *   <when-type-assignable class="org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext.Builder" />
   * </replace-with>
   *
   * }</pre>
   */
  public static class Builder {

    protected boolean failOnUnknownProperties = true;

    protected boolean acceptSingleValueAsArray = false;

    protected boolean wrapExceptions = true;

    protected boolean useSafeEval = true;

    protected boolean readUnknownEnumValuesAsNull = false;

    protected boolean useBrowserTimezone = false;

    private boolean readDateAsTimestamp = true;

    private Builder() {}

    /**
     * Determines whether encountering of unknown properties (ones that do not map to a property,
     * and there is no "any setter" or handler that can handle it) should result in a failure (by
     * throwing a {@link XMLDeserializationException}) or not. This setting only takes effect after
     * all other handling methods for unknown properties have been tried, and property remains
     * unhandled.
     *
     * <p>Feature is enabled by default (meaning that a {@link XMLDeserializationException} will be
     * thrown if an unknown property is encountered).
     *
     * @param failOnUnknownProperties true if should fail on unknown properties
     * @return the builder
     */
    public Builder failOnUnknownProperties(boolean failOnUnknownProperties) {
      this.failOnUnknownProperties = failOnUnknownProperties;
      return this;
    }

    /**
     * Feature that determines whether it is acceptable to coerce non-array (in JSON) values to work
     * with Java collection (arrays, java.util.Collection) types. If enabled, collection
     * deserializers will try to handle non-array values as if they had "implicit" surrounding JSON
     * array. This feature is meant to be used for compatibility/interoperability reasons, to work
     * with packages (such as XML-to-JSON converters) that leave out JSON array in cases where there
     * is just a single element in array.
     *
     * <p>Feature is disabled by default.
     *
     * @param acceptSingleValueAsArray true if should acceptSingleValueAsArray
     * @return the builder
     */
    public Builder acceptSingleValueAsArray(boolean acceptSingleValueAsArray) {
      this.acceptSingleValueAsArray = acceptSingleValueAsArray;
      return this;
    }

    /**
     * Feature that determines whether xml-mapper code should catch and wrap {@link
     * RuntimeException}s (but never {@link Error}s!) to add additional information about location
     * (within input) of problem or not. If enabled, exceptions will be caught and re-thrown; this
     * can be convenient both in that all exceptions will be checked and declared, and so there is
     * more contextual information. However, sometimes calling application may just want "raw"
     * unchecked exceptions passed as is. <br>
     * <br>
     * Feature is enabled by default.
     *
     * @param wrapExceptions true if should wrapExceptions
     * @return the builder
     */
    public Builder wrapExceptions(boolean wrapExceptions) {
      this.wrapExceptions = wrapExceptions;
      return this;
    }

    /**
     * to deserialize JsType <br>
     * <br>
     *
     * @param useSafeEval true if should useSafeEval
     * @return the builder
     */
    public Builder useSafeEval(boolean useSafeEval) {
      this.useSafeEval = useSafeEval;
      return this;
    }

    /**
     * Feature that determines whether xml-mapper should return null for unknown enum values.
     * Default is false which will throw {@link IllegalArgumentException} when unknown enum value is
     * found.
     *
     * @param readUnknownEnumValuesAsNull true if should readUnknownEnumValuesAsNull
     * @return the builder
     */
    public Builder readUnknownEnumValuesAsNull(boolean readUnknownEnumValuesAsNull) {
      this.readUnknownEnumValuesAsNull = readUnknownEnumValuesAsNull;
      return this;
    }

    /**
     * Feature that specifies whether dates that doesn't contain timezone information are
     * interpreted using the browser timezone or being relative to UTC (the default).
     *
     * @param useBrowserTimezone true if should use browser timezone
     * @return the builder
     */
    public Builder useBrowserTimezone(boolean useBrowserTimezone) {
      this.useBrowserTimezone = useBrowserTimezone;
      return this;
    }

    public Builder readDateAsTimestamp(boolean readDateAsTimestamp) {
      this.readDateAsTimestamp = readDateAsTimestamp;
      return this;
    }

    public final XMLDeserializationContext build() {
      return new DefaultXMLDeserializationContext(
          failOnUnknownProperties,
          acceptSingleValueAsArray,
          wrapExceptions,
          useSafeEval,
          readUnknownEnumValuesAsNull,
          useBrowserTimezone,
          readDateAsTimestamp);
    }
  }

  public static class DefaultBuilder extends Builder {

    private DefaultBuilder() {}
  }

  private static class XMLReaderHolder extends GWTXMLReaderHolder {

    @GwtIncompatible
    @Override
    public XMLReader newXMLReader(String input) throws XMLStreamException {
      return new DefaultXMLReader(input);
    }
  }

  private static class GWTXMLReaderHolder {

    public XMLReader newXMLReader(String input) throws XMLStreamException {
      return new JsNativeXMLReader(input);
    }
  }
}
