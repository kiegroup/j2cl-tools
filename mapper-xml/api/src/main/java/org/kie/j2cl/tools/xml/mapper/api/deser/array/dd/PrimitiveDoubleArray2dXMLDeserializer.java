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
 * Default {@link XMLDeserializer} implementation for 2D array of double.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveDoubleArray2dXMLDeserializer
    extends AbstractArray2dXMLDeserializer<double[][]> {

  /**
   * getInstance
   *
   * @return an instance of {@link PrimitiveDoubleArray2dXMLDeserializer}
   */
  public static PrimitiveDoubleArray2dXMLDeserializer getInstance() {
    return new PrimitiveDoubleArray2dXMLDeserializer();
  }

  private PrimitiveDoubleArray2dXMLDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public double[][] doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    List<List<Double>> list =
        deserializeIntoList(
            reader,
            ctx,
            s -> BaseNumberXMLDeserializer.DoubleXMLDeserializer.getInstance(),
            params);

    if (list.isEmpty()) {
      return new double[0][0];
    }

    List<Double> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new double[list.size()][0];
    }

    double[][] array = new double[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Double> innerList : list) {
      j = 0;
      for (Double value : innerList) {
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
