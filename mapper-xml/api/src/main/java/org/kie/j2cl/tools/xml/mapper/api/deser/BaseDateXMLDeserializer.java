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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.MapperContextProvider;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Base implementation of {@link XMLDeserializer} for dates.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseDateXMLDeserializer<D extends Date> extends XMLDeserializer<D> {

  @Override
  public D deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    if (ctx.isReadDateAsTimestamps()) {
      return deserializeNumber(Long.valueOf(value), params);
    }
    return deserializeString(value, ctx, params);
  }

  /** {@inheritDoc} */
  @Override
  public D doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    if (ctx.isReadDateAsTimestamps()) {
      return deserializeNumber(Long.valueOf(reader.nextLong()), params);
    }
    return deserializeString(reader.nextString(), ctx, params);
  }

  /**
   * deserializeNumber
   *
   * @param millis a long.
   * @param params a {@link XMLDeserializerParameters} object.
   * @return a D object.
   */
  protected abstract D deserializeNumber(long millis, XMLDeserializerParameters params);

  /**
   * deserializeString
   *
   * @param date a {@link java.lang.String} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @return a D object.
   */
  protected abstract D deserializeString(
      String date, XMLDeserializationContext ctx, XMLDeserializerParameters params);

  /** Default implementation of {@link BaseDateXMLDeserializer} for {@link Date} */
  public static final class DateXMLDeserializer extends BaseDateXMLDeserializer<Date> {

    private static final DateXMLDeserializer INSTANCE = new DateXMLDeserializer();

    private DateXMLDeserializer() {}

    /** @return an instance of {@link DateXMLDeserializer} */
    public static DateXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    public Date doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      if (ctx.isReadDateAsTimestamps()) {
        return deserializeNumber(Long.valueOf(reader.nextLong()), params);
      }
      return deserializeString(reader.nextString(), ctx, params);
    }

    @Override
    protected Date deserializeString(
        String date, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
      if (date == null || date.isEmpty()) {
        return null;
      }
      return MapperContextProvider.get()
          .dateFormat()
          .parse(ctx.isUseBrowserTimezone(), params.getPattern(), null, date);
    }

    @Override
    protected Date deserializeNumber(long millis, XMLDeserializerParameters params) {
      if (millis == 0) {
        return null;
      }
      return new Date(millis);
    }
  }

  /** Default implementation of {@link BaseDateXMLDeserializer} for {@link java.sql.Date} */
  public static final class SqlDateXMLDeserializer extends BaseDateXMLDeserializer<java.sql.Date> {

    private static final SqlDateXMLDeserializer INSTANCE = new SqlDateXMLDeserializer();

    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    private SqlDateXMLDeserializer() {}

    /** @return an instance of {@link SqlDateXMLDeserializer} */
    public static SqlDateXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected java.sql.Date deserializeString(
        String date, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
      if (date == null) {
        return null;
      }
      return new java.sql.Date(Long.valueOf(date));
      // return new
      // java.sql.Date(MapperContextProvider.get().dateFormat().parse(ctx.isUseBrowserTimezone(),
      // SQL_DATE_FORMAT, false, date).getTime());
    }

    @Override
    protected java.sql.Date deserializeNumber(long millis, XMLDeserializerParameters params) {
      return new java.sql.Date(millis);
    }
  }

  /** Default implementation of {@link BaseDateXMLDeserializer} for {@link Time} */
  public static final class SqlTimeXMLDeserializer extends BaseDateXMLDeserializer<Time> {

    private static final SqlTimeXMLDeserializer INSTANCE = new SqlTimeXMLDeserializer();

    private SqlTimeXMLDeserializer() {}

    /** @return an instance of {@link SqlTimeXMLDeserializer} */
    public static SqlTimeXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Time deserializeString(
        String date, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
      return Time.valueOf(date);
    }

    @Override
    protected Time deserializeNumber(long millis, XMLDeserializerParameters params) {
      return new Time(millis);
    }
  }

  /** Default implementation of {@link BaseDateXMLDeserializer} for {@link Timestamp} */
  public static final class SqlTimestampXMLDeserializer extends BaseDateXMLDeserializer<Timestamp> {

    private static final SqlTimestampXMLDeserializer INSTANCE = new SqlTimestampXMLDeserializer();

    private SqlTimestampXMLDeserializer() {}

    /** @return an instance of {@link SqlTimestampXMLDeserializer} */
    public static SqlTimestampXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Timestamp deserializeString(
        String date, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
      return new Timestamp(
          params
              .dateFormat()
              .parse(ctx.isUseBrowserTimezone(), params.getPattern(), null, date)
              .getTime());
    }

    @Override
    protected Timestamp deserializeNumber(long millis, XMLDeserializerParameters params) {
      return new Timestamp(millis);
    }
  }
}
