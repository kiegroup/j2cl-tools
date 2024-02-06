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

package org.kie.j2cl.tools.xml.mapper.api.deser.array;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveIntegerArrayXMLDeserializer extends AbstractArrayXMLDeserializer<int[]> {

  /**
   * getInstance
   *
   * @return an instance of {@link PrimitiveIntegerArrayXMLDeserializer}
   */
  public static PrimitiveIntegerArrayXMLDeserializer getInstance() {
    return new PrimitiveIntegerArrayXMLDeserializer();
  }

  private PrimitiveIntegerArrayXMLDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public int[] doDeserializeArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<Integer> list =
        deserializeIntoList(
            reader,
            ctx,
            s -> BaseNumberXMLDeserializer.IntegerXMLDeserializer.getInstance(),
            params);

    int[] result = new int[list.size()];
    int i = 0;
    for (Integer value : list) {
      if (null != value) {
        result[i] = value;
      }
      i++;
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected int[] doDeserializeSingleArray(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    return new int[] {
      BaseNumberXMLDeserializer.IntegerXMLDeserializer.getInstance()
          .deserialize(reader, ctx, params)
    };
  }
}
