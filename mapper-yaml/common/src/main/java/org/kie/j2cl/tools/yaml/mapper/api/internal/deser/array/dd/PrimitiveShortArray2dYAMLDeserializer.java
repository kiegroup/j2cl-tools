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
 * Default {@link YAMLDeserializer} implementation for 2D array of short.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveShortArray2dYAMLDeserializer
    extends AbstractArray2dYAMLDeserializer<short[][]> {

  public static final PrimitiveShortArray2dYAMLDeserializer INSTANCE =
      new PrimitiveShortArray2dYAMLDeserializer();

  /** {@inheritDoc} */
  @Override
  public short[][] deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {
    List<List<Short>> list =
        deserializeIntoList(
            yaml.getSequenceNode(key),
            ctx,
            BaseNumberYAMLDeserializer.ShortYAMLDeserializer.INSTANCE);

    if (list.isEmpty()) {
      return new short[0][0];
    }

    List<Short> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new short[list.size()][0];
    }

    short[][] array = new short[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Short> innerList : list) {
      j = 0;
      for (Short value : innerList) {
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
