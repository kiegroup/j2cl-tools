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

import java.util.LinkedHashSet;
import java.util.function.Function;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.LinkedHashSet}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class LinkedHashSetXMLDeserializer<T> extends BaseSetXMLDeserializer<LinkedHashSet<T>, T> {

  /**
   * newInstance
   *
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     java.util.LinkedHashSet}.
   * @param <T> Type of the elements inside the {@link java.util.LinkedHashSet}
   * @return a new instance of {@link LinkedHashSetXMLDeserializer}
   */
  public static <T> LinkedHashSetXMLDeserializer<T> newInstance(
      Function<String, XMLDeserializer<T>> deserializer) {
    return new LinkedHashSetXMLDeserializer<>(deserializer);
  }

  /**
   * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link
   *     LinkedHashSet}.
   */
  private LinkedHashSetXMLDeserializer(Function<String, XMLDeserializer<T>> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected LinkedHashSet<T> newCollection() {
    return new LinkedHashSet<>();
  }
}
