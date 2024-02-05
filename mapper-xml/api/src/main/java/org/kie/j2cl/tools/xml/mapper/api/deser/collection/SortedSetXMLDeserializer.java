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

package org.kie.j2cl.tools.xml.mapper.api.deser.collection;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.SortedSet}. The
 * deserialization process returns a {@link java.util.TreeSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.SortedSet}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class SortedSetXMLDeserializer<T>
    extends BaseSortedSetXMLDeserializer<SortedSet<T>, T> {

  /**
   * newInstance
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     java.util.SortedSet}.
   * @param <T> Type of the elements inside the {@link java.util.SortedSet}
   * @return a new instance of {@link SortedSetXMLDeserializer}
   */
  public static <T> SortedSetXMLDeserializer<T> newInstance(
      Function<String, XMLDeserializer<T>> deserializer) {
    return new SortedSetXMLDeserializer<>(deserializer);
  }

  /**
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     SortedSet}.
   */
  private SortedSetXMLDeserializer(Function<String, XMLDeserializer<T>> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected SortedSet<T> newCollection() {
    return new TreeSet<>();
  }
}
