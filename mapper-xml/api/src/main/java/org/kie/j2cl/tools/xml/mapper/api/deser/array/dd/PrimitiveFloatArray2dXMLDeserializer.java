/*
 * Copyright 2014 Nicolas Morel
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

package org.kie.j2cl.tools.xml.mapper.api.deser.array.dd;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.deser.BaseNumberXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for 2D array of float.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveFloatArray2dXMLDeserializer
    extends AbstractArray2dXMLDeserializer<float[][]> {

  /**
   * getInstance
   *
   * @return an instance of {@link PrimitiveFloatArray2dXMLDeserializer}
   */
  public static PrimitiveFloatArray2dXMLDeserializer getInstance() {
    return new PrimitiveFloatArray2dXMLDeserializer();
  }

  private PrimitiveFloatArray2dXMLDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public float[][] doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<List<Float>> list =
        deserializeIntoList(
            reader, ctx, s -> BaseNumberXMLDeserializer.FloatXMLDeserializer.getInstance(), params);

    if (list.isEmpty()) {
      return new float[0][0];
    }

    List<Float> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new float[list.size()][0];
    }

    float[][] array = new float[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Float> innerList : list) {
      j = 0;
      for (Float value : innerList) {
        if (null != value) {
          array[i][j] = value;
        }
        j++;
      }
      i++;
    }
    return array;
  }
}
