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

import java.util.ArrayList;
import java.util.List;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.StringYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.utils.Base64Utils;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;

/**
 * Default {@link YAMLDeserializer} implementation for 2D array of byte.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveByteArray2dYAMLDeserializer
    extends AbstractArray2dYAMLDeserializer<byte[][]> {

  public static final PrimitiveByteArray2dYAMLDeserializer INSTANCE =
      new PrimitiveByteArray2dYAMLDeserializer();

  /** {@inheritDoc} */
  @Override
  public byte[][] deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx) {

    byte[][] result;

    List<String> strings =
        doDeserializeInnerIntoList(yaml.getSequenceNode(key), ctx, StringYAMLDeserializer.INSTANCE);

    if (strings.isEmpty()) {
      result = new byte[0][0];
    } else {
      List<byte[]> list = new ArrayList<>();

      int size = 0;
      for (String string : strings) {
        byte[] decoded = Base64Utils.fromBase64(string);
        size = Math.max(size, decoded.length);
        list.add(decoded);
      }

      result = new byte[list.size()][size];
      int i = 0;
      for (byte[] value : list) {
        if (null != value) {
          result[i] = value;
        }
        i++;
      }
    }
    return result;
  }
}
