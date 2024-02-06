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
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.BooleanYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;

/**
 * Default {@link YAMLDeserializer} implementation for 2D array of boolean.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveBooleanArray2dYAMLDeserializer
    extends AbstractArray2dYAMLDeserializer<boolean[][]> {

  public static final PrimitiveBooleanArray2dYAMLDeserializer INSTANCE =
      new PrimitiveBooleanArray2dYAMLDeserializer();

  /** {@inheritDoc} */
  @Override
  public boolean[][] deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {
    List<List<Boolean>> list =
        deserializeIntoList(yaml.getSequenceNode(key), ctx, BooleanYAMLDeserializer.INSTANCE);

    if (list.isEmpty()) {
      return new boolean[0][0];
    }

    List<Boolean> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new boolean[list.size()][0];
    }

    boolean[][] array = new boolean[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Boolean> innerList : list) {
      j = 0;
      for (Boolean value : innerList) {
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
