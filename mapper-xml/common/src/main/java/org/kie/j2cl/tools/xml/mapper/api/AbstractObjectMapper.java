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

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLSerializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLWriter;

/**
 * Base implementation of {@link ObjectMapper}. It delegates the serialization/deserialization to a
 * serializer/deserializer.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractObjectMapper<T> implements ObjectMapper<T> {

  private final String rootName;

  private XMLDeserializer<T> deserializer;

  private XMLSerializer<T> serializer;

  /**
   * Constructor for AbstractObjectMapper.
   *
   * @param rootName a {@link java.lang.String} object.
   */
  protected AbstractObjectMapper(String rootName) {
    this.rootName = rootName;
  }

  /** {@inheritDoc} */
  @Override
  public T read(String in) throws XMLDeserializationException, XMLStreamException {
    return read(in, DefaultXMLDeserializationContext.builder().build());
  }

  /** {@inheritDoc} */
  public T read(String in, XMLDeserializationContext ctx)
      throws XMLDeserializationException, XMLStreamException {
    XMLReader reader = ctx.newXMLReader(in);

    try {
      return getDeserializer(reader).deserialize(reader, ctx);
    } catch (XMLDeserializationException e) {
      // already logged, we just throw it
      throw e;
    } catch (RuntimeException e) {
      throw ctx.traceError(e, reader);
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>deserializer</code>.
   */
  @Override
  public XMLDeserializer<T> getDeserializer(XMLReader reader) {
    if (null == deserializer) {
      deserializer = newDeserializer(reader);
    }
    return deserializer;
  }

  /**
   * Instantiates a new deserializer
   *
   * @return a new deserializer
   */
  protected abstract XMLDeserializer<T> newDeserializer(XMLReader reader);

  /** {@inheritDoc} */
  @Override
  public String write(T value) throws XMLSerializationException, XMLStreamException {
    return write(value, DefaultXMLSerializationContext.builder().build());
  }

  /** {@inheritDoc} */
  public String write(T value, XMLSerializationContext ctx)
      throws XMLSerializationException, XMLStreamException {
    XMLWriter writer = ctx.newXMLWriter();
    try {
      getSerializer().serialize(writer, value, ctx);
      return writer.getOutput();
    } catch (XMLSerializationException e) {
      // already logged, we just throw it
      throw new Error(e);
    } catch (RuntimeException e) {
      throw ctx.traceError(value, e, writer);
    } catch (Exception e) {
      throw new Error(e);
    } finally {
      writer.close();
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>Getter for the field <code>serializer</code>.
   */
  @Override
  public XMLSerializer<T> getSerializer() {
    if (null == serializer) {
      serializer = (XMLSerializer<T>) newSerializer();
    }
    return serializer;
  }

  /**
   * Instantiates a new serializer
   *
   * @return a new serializer
   */
  protected abstract XMLSerializer<?> newSerializer();
}
