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

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;
import org.kie.j2cl.tools.xml.mapper.api.utils.NumberUtils;

/**
 * Base implementation of {@link XMLDeserializer} for {@link java.lang.Number}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseNumberXMLDeserializer<N extends Number> extends XMLDeserializer<N> {

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link BigDecimal} */
  public static final class BigDecimalXMLDeserializer
      extends BaseNumberXMLDeserializer<BigDecimal> {

    private static final BigDecimalXMLDeserializer INSTANCE = new BigDecimalXMLDeserializer();

    private BigDecimalXMLDeserializer() {}

    /** @return an instance of {@link BigDecimalXMLDeserializer} */
    public static BigDecimalXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected BigDecimal doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return new BigDecimal(value);
    }

    @Override
    public BigDecimal deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return new BigDecimal(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link BigInteger} */
  public static final class BigIntegerXMLDeserializer
      extends BaseNumberXMLDeserializer<BigInteger> {

    private static final BigIntegerXMLDeserializer INSTANCE = new BigIntegerXMLDeserializer();

    private BigIntegerXMLDeserializer() {}

    /** @return an instance of {@link BigIntegerXMLDeserializer} */
    public static BigIntegerXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected BigInteger doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return new BigInteger(value);
    }

    @Override
    public BigInteger deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return new BigInteger(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Byte} */
  public static final class ByteXMLDeserializer extends BaseNumberXMLDeserializer<Byte> {

    private static final ByteXMLDeserializer INSTANCE = new ByteXMLDeserializer();

    private ByteXMLDeserializer() {}

    /** @return an instance of {@link ByteXMLDeserializer} */
    public static ByteXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Byte doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Byte.valueOf(value);
    }

    @Override
    public Byte deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Byte.valueOf(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Double} */
  public static final class DoubleXMLDeserializer extends BaseNumberXMLDeserializer<Double> {

    private static final DoubleXMLDeserializer INSTANCE = new DoubleXMLDeserializer();

    private DoubleXMLDeserializer() {}

    /** @return an instance of {@link DoubleXMLDeserializer} */
    public static DoubleXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Double doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Double.valueOf(value);
    }

    @Override
    public Double deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Double.valueOf(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Float} */
  public static final class FloatXMLDeserializer extends BaseNumberXMLDeserializer<Float> {

    private static final FloatXMLDeserializer INSTANCE = new FloatXMLDeserializer();

    private FloatXMLDeserializer() {}

    /** @return an instance of {@link FloatXMLDeserializer} */
    public static FloatXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Float doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Float.parseFloat(value);
    }

    @Override
    public Float deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Float.parseFloat(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Integer} */
  public static final class IntegerXMLDeserializer extends BaseNumberXMLDeserializer<Integer> {

    private static final IntegerXMLDeserializer INSTANCE = new IntegerXMLDeserializer();

    private IntegerXMLDeserializer() {}

    /** @return an instance of {@link IntegerXMLDeserializer} */
    public static IntegerXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Integer doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Integer.valueOf(value);
    }

    @Override
    public Integer deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Integer.valueOf(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Long} */
  public static final class LongXMLDeserializer extends BaseNumberXMLDeserializer<Long> {

    private static final LongXMLDeserializer INSTANCE = new LongXMLDeserializer();

    private LongXMLDeserializer() {}

    /** @return an instance of {@link LongXMLDeserializer} */
    public static LongXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Long doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Long.valueOf(value);
    }

    @Override
    public Long deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Long.valueOf(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Short} */
  public static final class ShortXMLDeserializer extends BaseNumberXMLDeserializer<Short> {

    private static final ShortXMLDeserializer INSTANCE = new ShortXMLDeserializer();

    private ShortXMLDeserializer() {}

    /** @return an instance of {@link ShortXMLDeserializer} */
    public static ShortXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    protected Short doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      String value = reader.nextString();
      if (value == null) {
        return null;
      }
      return Short.valueOf(value);
    }

    @Override
    public Short deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return Short.valueOf(value);
    }
  }

  /** Default implementation of {@link BaseNumberXMLDeserializer} for {@link Number} */
  public static final class NumberXMLDeserializer extends BaseNumberXMLDeserializer<Number> {

    private static final NumberXMLDeserializer INSTANCE = new NumberXMLDeserializer();

    private NumberXMLDeserializer() {}

    /** @return an instance of {@link NumberXMLDeserializer} */
    public static NumberXMLDeserializer getInstance() {
      return INSTANCE;
    }

    @Override
    public Number doDeserialize(
        XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLStreamException {
      return reader.nextNumber();
    }

    @Override
    public Number deserialize(
        String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
        throws XMLDeserializationException {
      if (value.isEmpty()) {
        return null;
      }
      return NumberUtils.toNumber(value);
    }
  }
}
