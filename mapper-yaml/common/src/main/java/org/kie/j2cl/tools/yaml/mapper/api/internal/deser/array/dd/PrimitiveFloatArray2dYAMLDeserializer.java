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

package org.kie.j2cl.tools.yaml.mapper.api.internal.deser.array.dd;

import java.util.List;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.BaseNumberYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;

/**
 * Default {@link YAMLDeserializer} implementation for 2D array of float.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveFloatArray2dYAMLDeserializer
    extends AbstractArray2dYAMLDeserializer<float[][]> {

  public static final PrimitiveFloatArray2dYAMLDeserializer INSTANCE =
      new PrimitiveFloatArray2dYAMLDeserializer();

  /** {@inheritDoc} */
  @Override
  public float[][] deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {
    List<List<Float>> list =
        deserializeIntoList(
            yaml.getSequenceNode(key),
            ctx,
            BaseNumberYAMLDeserializer.FloatYAMLDeserializer.INSTANCE);

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
