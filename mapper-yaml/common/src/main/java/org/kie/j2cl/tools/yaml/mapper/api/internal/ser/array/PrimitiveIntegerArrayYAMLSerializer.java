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

package org.kie.j2cl.tools.yaml.mapper.api.internal.ser.array;

import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.AbstractYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.BaseNumberYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;

/**
 * Default {@link AbstractYAMLSerializer} implementation for array of int.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveIntegerArrayYAMLSerializer extends AbstractYAMLSerializer<int[]> {

  public static final PrimitiveIntegerArrayYAMLSerializer INSTANCE =
      new PrimitiveIntegerArrayYAMLSerializer();

  private final BaseNumberYAMLSerializer.IntegerYAMLSerializer serializer =
      BaseNumberYAMLSerializer.IntegerYAMLSerializer.INSTANCE;

  @Override
  protected boolean isEmpty(int[] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void serialize(
      YamlMapping writer, String propertyName, int[] values, YAMLSerializationContext ctx) {
    if (isEmpty(values)) {
      if (ctx.isWriteEmptyYAMLArrays()) {
        writer.addScalarNode(propertyName, new int[] {});
      }
      return;
    }
    YamlSequence yamlSequence = writer.addSequenceNode(propertyName);
    serialize(yamlSequence, values, ctx);
  }

  @Override
  public void serialize(YamlSequence writer, int[] value, YAMLSerializationContext ctx) {
    for (int i : value) {
      serializer.serialize(writer, i, ctx);
    }
  }
}
