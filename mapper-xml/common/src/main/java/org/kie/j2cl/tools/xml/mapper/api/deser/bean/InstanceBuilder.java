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

package org.kie.j2cl.tools.xml.mapper.api.deser.bean;

import java.util.Map;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * InstanceBuilder interface.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public interface InstanceBuilder<T> {

  /**
   * newInstance
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @param bufferedProperties a {@link Map} object.
   * @param bufferedPropertiesValues a {@link Map} object.
   * @return a {@link deser.bean.Instance} object.
   */
  Instance<T> newInstance(
      XMLReader reader,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params,
      Map<String, String> bufferedProperties,
      Map<String, Object> bufferedPropertiesValues);

  /**
   * getParametersDeserializer
   *
   * @return a {@link deser.bean.MapLike} object.
   */
  MapLike<HasDeserializerAndParameters> getParametersDeserializer();
}
