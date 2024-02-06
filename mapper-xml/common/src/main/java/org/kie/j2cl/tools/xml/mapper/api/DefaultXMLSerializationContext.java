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

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLSerializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;
import org.kie.j2cl.tools.xml.mapper.api.stream.impl.DefaultXMLWriter;
import org.kie.j2cl.tools.xml.mapper.api.stream.impl.JsNativeXMLWriter;

/**
 * Context for the serialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class DefaultXMLSerializationContext implements XMLSerializationContext {

  private static final Logger logger = Logger.getLogger("XMLSerialization");
  /*
   * Serialization options
   */
  private final boolean serializeNulls;
  private final boolean writeDatesAsTimestamps;
  private final boolean writeDateKeysAsTimestamps;
  private final boolean writeNullMapValues;
  private final boolean writeEmptyXMLArrays;
  private final boolean orderMapEntriesByKeys;
  private final boolean wrapExceptions;

  private DefaultXMLSerializationContext(
      boolean serializeNulls,
      boolean writeDatesAsTimestamps,
      boolean writeDateKeysAsTimestamps,
      boolean writeNullMapValues,
      boolean writeEmptyXMLArrays,
      boolean orderMapEntriesByKeys,
      boolean wrapExceptions) {
    this.serializeNulls = serializeNulls;
    this.writeDatesAsTimestamps = writeDatesAsTimestamps;
    this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
    this.writeNullMapValues = writeNullMapValues;
    this.writeEmptyXMLArrays = writeEmptyXMLArrays;
    this.orderMapEntriesByKeys = orderMapEntriesByKeys;
    this.wrapExceptions = wrapExceptions;
  }

  /**
   * builder
   *
   * @return a {@link Builder} object.
   */
  public static Builder builder() {
    return new DefaultBuilder();
  }

  /**
   * {@inheritDoc}
   *
   * <p>isSerializeNulls
   *
   * @see Builder#serializeNulls(boolean)
   */
  @Override
  public boolean isSerializeNulls() {
    return serializeNulls;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isWriteDatesAsTimestamps
   *
   * @see Builder#writeDatesAsTimestamps(boolean)
   */
  @Override
  public boolean isWriteDatesAsTimestamps() {
    return writeDatesAsTimestamps;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isWriteDateKeysAsTimestamps
   *
   * @see Builder#writeDateKeysAsTimestamps(boolean)
   */
  @Override
  public boolean isWriteDateKeysAsTimestamps() {
    return writeDateKeysAsTimestamps;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isWriteNullMapValues
   *
   * @see Builder#writeNullMapValues(boolean)
   */
  @Override
  public boolean isWriteNullMapValues() {
    return writeNullMapValues;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isWriteEmptyXMLArrays
   *
   * @see Builder#writeEmptyXMLArrays(boolean)
   */
  @Override
  public boolean isWriteEmptyXMLArrays() {
    return writeEmptyXMLArrays;
  }

  /**
   * {@inheritDoc}
   *
   * <p>isOrderMapEntriesByKeys
   *
   * @see Builder#orderMapEntriesByKeys(boolean)
   */
  @Override
  public boolean isOrderMapEntriesByKeys() {
    return orderMapEntriesByKeys;
  }

  /**
   * {@inheritDoc}
   *
   * <p>newXMLWriter
   */
  @Override
  public XMLWriter newXMLWriter() throws XMLStreamException {
    return new XMLWriterHolder().newXMLReader();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error and returns a corresponding exception.
   */
  @Override
  public XMLSerializationException traceError(Object value, String message) {
    getLogger().log(Level.SEVERE, message);
    return new XMLSerializationException(message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error with current writer state and returns a corresponding exception.
   */
  @Override
  public XMLSerializationException traceError(Object value, String message, XMLWriter writer) {
    XMLSerializationException exception = traceError(value, message);
    traceWriterInfo(value, writer);
    return exception;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error and returns a corresponding exception.
   */
  @Override
  public RuntimeException traceError(Object value, RuntimeException cause) {
    getLogger().log(Level.SEVERE, "Error during serialization", cause);
    if (wrapExceptions) {
      return new XMLSerializationException(cause);
    } else {
      return cause;
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>Trace an error with current writer state and returns a corresponding exception.
   */
  @Override
  public RuntimeException traceError(Object value, RuntimeException cause, XMLWriter writer) {
    RuntimeException exception = traceError(value, cause);
    traceWriterInfo(value, writer);
    return exception;
  }

  /** {@inheritDoc} */
  @Override
  public XMLSerializerParameters defaultParameters() {
    return MapperContextProvider.get().defaultSerializerParameters();
  }

  /**
   * Trace the current writer state
   *
   * @param value current value
   */
  private void traceWriterInfo(Object value, XMLWriter writer) {
    if (getLogger().isLoggable(Level.INFO)) {
      getLogger()
          .log(
              Level.INFO,
              "Error on value <" + value + ">. Current output : <" + writer.getOutput() + ">");
    }
  }

  /** {@inheritDoc} */
  @Override
  public Logger getLogger() {
    return logger;
  }

  /**
   * Builder for {@link XMLSerializationContext}. To override default settings globally, you can
   * extend this class, modify the default settings inside the constructor and tell the compiler to
   * use your builder instead in your gwt.xml file :
   *
   * <pre>{@code
   * <replace-with class="your.package.YourBuilder">
   *   <when-type-assignable class="org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext.Builder" />
   * </replace-with>
   *
   * }</pre>
   */
  public static class Builder {

    protected boolean useEqualityForObjectId = false;

    protected boolean serializeNulls = false;

    protected boolean writeDatesAsTimestamps = true;

    protected boolean writeDateKeysAsTimestamps = false;

    protected boolean writeNullMapValues = true;

    protected boolean writeEmptyXMLArrays = true;

    protected boolean orderMapEntriesByKeys = false;

    protected boolean wrapExceptions = true;

    protected boolean wrapCollections = true;

    /**
     * Determines whether Object Identity is compared using true JVM-level identity of Object
     * (false); or, <code>equals()</code> method. Latter is sometimes useful when dealing with
     * Database-bound objects with ORM libraries (like Hibernate).
     *
     * <p>Option is disabled by default; meaning that strict identity is used, not <code>equals()
     * </code>
     *
     * @param useEqualityForObjectId true if should useEqualityForObjectId
     * @return the builder
     */
    public Builder useEqualityForObjectId(boolean useEqualityForObjectId) {
      this.useEqualityForObjectId = useEqualityForObjectId;
      return this;
    }

    /**
     * Sets whether object members are serialized when their value is null. This has no impact on
     * array elements. The default is true.
     *
     * @param serializeNulls true if should serializeNulls
     * @return the builder
     */
    public Builder serializeNulls(boolean serializeNulls) {
      this.serializeNulls = serializeNulls;
      return this;
    }

    /**
     * Determines whether {@link Date} and {@link java.sql.Timestamp} values are to be serialized as
     * numeric timestamps (true; the default), or as textual representation.
     *
     * <p>If textual representation is used, the actual format is Option is enabled by default.
     *
     * @param writeDatesAsTimestamps true if should writeDatesAsTimestamps
     * @return the builder
     */
    public Builder writeDatesAsTimestamps(boolean writeDatesAsTimestamps) {
      this.writeDatesAsTimestamps = writeDatesAsTimestamps;
      return this;
    }

    /**
     * Feature that determines whether {@link Date}s and {@link java.sql.Timestamp}s used as {@link
     * Map} keys are serialized as timestamps or as textual values.
     *
     * <p>If textual representation is used, the actual format is Option is disabled by default.
     *
     * @param writeDateKeysAsTimestamps true if should writeDateKeysAsTimestamps
     * @return the builder
     */
    public Builder writeDateKeysAsTimestamps(boolean writeDateKeysAsTimestamps) {
      this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
      return this;
    }

    /**
     * Feature that determines whether Map entries with null values are to be serialized (true) or
     * not (false).
     *
     * <p>Feature is enabled by default.
     *
     * @param writeNullMapValues true if should writeNullMapValues
     * @return the builder
     */
    public Builder writeNullMapValues(boolean writeNullMapValues) {
      this.writeNullMapValues = writeNullMapValues;
      return this;
    }

    /**
     * Feature that determines whether Container properties (POJO properties with declared value of
     * Collection or array; i.e. things that produce XML arrays) that are empty (have no elements)
     * will be serialized as empty XML arrays (true), or suppressed from output (false).
     *
     * <p>Note that this does not change behavior of {@link Map}s, or "Collection-like" types.
     *
     * <p>Feature is enabled by default.
     *
     * @param writeEmptyXMLArrays true if should writeEmptyXMLArrays
     * @return the builder
     */
    public Builder writeEmptyXMLArrays(boolean writeEmptyXMLArrays) {
      this.writeEmptyXMLArrays = writeEmptyXMLArrays;
      return this;
    }

    /**
     * Feature that determines whether {@link Map} entries are first sorted by key before
     * serialization or not: if enabled, additional sorting step is performed if necessary (not
     * necessary for {@link SortedMap}s), if disabled, no additional sorting is needed.
     *
     * <p>Feature is disabled by default.
     *
     * @param orderMapEntriesByKeys true if should orderMapEntriesByKeys
     * @return the builder
     */
    public Builder orderMapEntriesByKeys(boolean orderMapEntriesByKeys) {
      this.orderMapEntriesByKeys = orderMapEntriesByKeys;
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

    public final XMLSerializationContext build() {
      return new DefaultXMLSerializationContext(
          serializeNulls,
          writeDatesAsTimestamps,
          writeDateKeysAsTimestamps,
          writeNullMapValues,
          writeEmptyXMLArrays,
          orderMapEntriesByKeys,
          wrapExceptions);
    }
  }

  public static class DefaultBuilder extends Builder {

    private DefaultBuilder() {}
  }

  private static class XMLWriterHolder extends XMLWriterHolderHolder {

    @GwtIncompatible
    @Override
    public XMLWriter newXMLReader() throws XMLStreamException {
      return new DefaultXMLWriter();
    }
  }

  private static class XMLWriterHolderHolder {

    public XMLWriter newXMLReader() throws XMLStreamException {
      return new JsNativeXMLWriter();
    }
  }
}
