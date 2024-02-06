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
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.CharacterYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;

/**
 * Default {@link YAMLDeserializer} implementation for 2D array of char.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveCharacterArray2dYAMLDeserializer
    extends AbstractArray2dYAMLDeserializer<char[][]> {

  public static final PrimitiveCharacterArray2dYAMLDeserializer INSTANCE =
      new PrimitiveCharacterArray2dYAMLDeserializer();

  /** {@inheritDoc} */
  @Override
  public char[][] deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {
    List<List<Character>> list =
        deserializeIntoList(yaml.getSequenceNode(key), ctx, CharacterYAMLDeserializer.INSTANCE);

    if (list.isEmpty()) {
      return new char[0][0];
    }

    List<Character> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new char[list.size()][0];
    }

    char[][] array = new char[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Character> innerList : list) {
      j = 0;
      for (Character value : innerList) {
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
