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

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Base implementation of {@link XMLSerializer} for {@link Number}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseNumberXMLSerializer<N extends Number> extends XMLSerializer<N> {

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      XMLWriter writer, N value, XMLSerializationContext ctx, XMLSerializerParameters params)
      throws XMLStreamException {
    if (isAttribute) {
      writeAttribute(writer, value.toString());
      isAttribute = false;
    } else {
      writeValue(writer, value.toString());
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link BigDecimal} */
  public static final class BigDecimalXMLSerializer extends BaseNumberXMLSerializer<BigDecimal> {

    private BigDecimalXMLSerializer() {}

    /** @return an instance of {@link BigDecimalXMLSerializer} */
    public static BigDecimalXMLSerializer getInstance() {
      return new BigDecimalXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link BigInteger} */
  public static final class BigIntegerXMLSerializer extends BaseNumberXMLSerializer<BigInteger> {

    private BigIntegerXMLSerializer() {}

    /** @return an instance of {@link BigIntegerXMLSerializer} */
    public static BigIntegerXMLSerializer getInstance() {
      return new BigIntegerXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Byte} */
  public static final class ByteXMLSerializer extends BaseNumberXMLSerializer<Byte> {

    private ByteXMLSerializer() {}

    /** @return an instance of {@link ByteXMLSerializer} */
    public static ByteXMLSerializer getInstance() {
      return new ByteXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Double} */
  public static final class DoubleXMLSerializer extends BaseNumberXMLSerializer<Double> {

    private DoubleXMLSerializer() {}

    /** @return an instance of {@link DoubleXMLSerializer} */
    public static DoubleXMLSerializer getInstance() {
      return new DoubleXMLSerializer();
    }

    @Override
    public void doSerialize(
        XMLWriter writer, Double value, XMLSerializationContext ctx, XMLSerializerParameters params)
        throws XMLStreamException {
      // writer has a special method to write double, let's use instead of default Number method.
      if (isAttribute) {
        writeAttribute(writer, params.doubleValue(value));
        isAttribute = false;
      } else {
        writeValue(writer, value.toString());
      }
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Float} */
  public static final class FloatXMLSerializer extends BaseNumberXMLSerializer<Float> {

    private FloatXMLSerializer() {}

    /** @return an instance of {@link FloatXMLSerializer} */
    public static FloatXMLSerializer getInstance() {
      return new FloatXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Integer} */
  public static final class IntegerXMLSerializer extends BaseNumberXMLSerializer<Integer> {

    private IntegerXMLSerializer() {}

    /** @return an instance of {@link IntegerXMLSerializer} */
    public static IntegerXMLSerializer getInstance() {
      return new IntegerXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Long} */
  public static final class LongXMLSerializer extends BaseNumberXMLSerializer<Long> {

    private LongXMLSerializer() {}

    /** @return an instance of {@link LongXMLSerializer} */
    public static LongXMLSerializer getInstance() {
      return new LongXMLSerializer();
    }

    @Override
    public void doSerialize(
        XMLWriter writer, Long value, XMLSerializationContext ctx, XMLSerializerParameters params)
        throws XMLStreamException {
      // writer has a special method to write long, let's use instead of default Number method.
      if (isAttribute) {
        writeAttribute(writer, value.toString());
        isAttribute = false;
      } else {
        writeValue(writer, value.toString());
      }
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Short} */
  public static final class ShortXMLSerializer extends BaseNumberXMLSerializer<Short> {

    private ShortXMLSerializer() {}

    /** @return an instance of {@link ShortXMLSerializer} */
    public static ShortXMLSerializer getInstance() {
      return new ShortXMLSerializer();
    }
  }

  /** Default implementation of {@link BaseNumberXMLSerializer} for {@link Number} */
  public static final class NumberXMLSerializer extends BaseNumberXMLSerializer<Number> {

    private NumberXMLSerializer() {}

    /** @return an instance of {@link NumberXMLSerializer} */
    public static NumberXMLSerializer getInstance() {
      return new NumberXMLSerializer();
    }
  }
}
