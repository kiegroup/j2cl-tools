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
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;

/**
 * Base implementation of {@link YAMLDeserializer} for array.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractArray2dYAMLDeserializer<T> implements YAMLDeserializer<T> {

  /**
   * Deserializes the array into a {@link java.util.List}. We need the length of the array before
   * creating it.
   *
   * @param sequence reader
   * @param ctx context of the deserialization process
   * @param deserializer deserializer for element inside the array
   * @param <C> type of the element inside the array
   * @return a list containing all the elements of the array
   */
  protected <C> List<List<C>> deserializeIntoList(
      YamlSequence sequence, YAMLDeserializationContext ctx, YAMLDeserializer<C> deserializer) {
    return doDeserializeIntoList(sequence, ctx, deserializer);
  }

  /**
   * doDeserializeIntoList
   *
   * @param sequence a {@link YamlSequence} object.
   * @param ctx a {@link YAMLDeserializationContext} object.
   * @param deserializer a {@link YAMLDeserializer} object.
   * @param <C> a C object.
   * @return a {@link java.util.List} object.
   */
  protected <C> List<List<C>> doDeserializeIntoList(
      YamlSequence sequence, YAMLDeserializationContext ctx, YAMLDeserializer<C> deserializer) {
    List<List<C>> list = new ArrayList<>();
    // we keep the size of the first inner list to initialize the next lists with the correct size
    // return list;
    throw new UnsupportedOperationException();
  }

  protected <C> List<C> doDeserializeInnerIntoList(
      YamlSequence sequence, YAMLDeserializationContext ctx, YAMLDeserializer<C> deserializer) {
    /*        List<C> innerList = new ArrayList<>();
    ctx.iterator().iterateOverCollection(reader, (Collection<T>) innerList, (reader1, ctx1, instance) -> {
        C val = deserializer.deserialize(reader1, ctx1, params);
        innerList.add(val);
        return null;
    }, ctx, params);
    return innerList;*/
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public T deserialize(YamlNode node, YAMLDeserializationContext ctx) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
