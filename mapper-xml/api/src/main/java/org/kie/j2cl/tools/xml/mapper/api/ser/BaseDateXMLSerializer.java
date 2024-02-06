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

package org.kie.j2cl.tools.xml.mapper.api.ser;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.MapperContextProvider;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Base implementation of {@link XMLSerializer} for dates.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseDateXMLSerializer<D extends Date> extends XMLSerializer<D> {

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(D value) {
    return null == value || value.getTime() == 0l;
  }

  /** Default implementation of {@link BaseDateXMLSerializer} for {@link Date} */
  public static final class DateXMLSerializer extends BaseDateXMLSerializer<Date> {

    private static final DateXMLSerializer INSTANCE = new DateXMLSerializer();

    private DateXMLSerializer() {}

    /** @return an instance of {@link DateXMLSerializer} */
    public static DateXMLSerializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected void doSerialize(
        XMLWriter writer, Date value, XMLSerializationContext ctx, XMLSerializerParameters params)
        throws XMLStreamException {
      String date;

      if ((ctx.isWriteDatesAsTimestamps())) {
        date = value.getTime() + "";
      } else {
        date = MapperContextProvider.get().dateFormat().format(params, value);
      }

      if (isAttribute) {
        writeAttribute(writer, date);
        isAttribute = false;
      } else {
        writeValue(writer, date);
      }
    }
  }

  /** Default implementation of {@link BaseDateXMLSerializer} for {@link java.sql.Date} */
  public static final class SqlDateXMLSerializer extends BaseDateXMLSerializer<java.sql.Date> {

    private static final SqlDateXMLSerializer INSTANCE = new SqlDateXMLSerializer();

    private SqlDateXMLSerializer() {}

    /** @return an instance of {@link SqlDateXMLSerializer} */
    public static SqlDateXMLSerializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected void doSerialize(
        XMLWriter writer,
        java.sql.Date value,
        XMLSerializationContext ctx,
        XMLSerializerParameters params)
        throws XMLStreamException {
      if (isAttribute) {
        writer.writeAttribute(propertyName, value.toString());
        isAttribute = false;
      } else {
        writer.unescapeValue(value.toString());
      }
    }
  }

  /** Default implementation of {@link BaseDateXMLSerializer} for {@link Date} */
  public static final class SqlTimeXMLSerializer extends BaseDateXMLSerializer<Time> {

    private static final SqlTimeXMLSerializer INSTANCE = new SqlTimeXMLSerializer();

    private SqlTimeXMLSerializer() {}

    /** @return an instance of {@link SqlTimeXMLSerializer} */
    public static SqlTimeXMLSerializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected void doSerialize(
        XMLWriter writer, Time value, XMLSerializationContext ctx, XMLSerializerParameters params)
        throws XMLStreamException {
      if (isAttribute) {
        writer.writeAttribute(propertyName, value.toString());
        isAttribute = false;
      } else {
        writer.unescapeValue(value.toString());
      }
    }
  }

  /** Default implementation of {@link BaseDateXMLSerializer} for {@link Timestamp} */
  public static final class SqlTimestampXMLSerializer extends BaseDateXMLSerializer<Timestamp> {

    private static final SqlTimestampXMLSerializer INSTANCE = new SqlTimestampXMLSerializer();

    private SqlTimestampXMLSerializer() {}

    /** @return an instance of {@link SqlTimestampXMLSerializer} */
    public static SqlTimestampXMLSerializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected void doSerialize(
        XMLWriter writer,
        Timestamp value,
        XMLSerializationContext ctx,
        XMLSerializerParameters params)
        throws XMLStreamException {
      if (isAttribute) {
        writer.writeAttribute(propertyName, value.getTime() + "");
        isAttribute = false;
      } else {
        if (ctx.isWriteDatesAsTimestamps()) {
          writer.value(value.getTime());
        } else {
          String date = MapperContextProvider.get().dateFormat().format(params, value);
          if (null == params.getPattern()) {
            writer.unescapeValue(date);
          } else {
            writer.value(date);
          }
        }
      }
    }
  }
}
