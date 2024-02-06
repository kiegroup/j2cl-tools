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

package org.kie.j2cl.tools.xml.mapper.api.deser.bean;

import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/**
 * Interface hiding the actual implementation doing the bean deserialization.
 *
 * @author Nicolas Morel.
 */
interface InternalDeserializer<T, S extends XMLDeserializer<T>> {

  /**
   * getDeserializer
   *
   * @return a S object.
   */
  S getDeserializer();

  /**
   * deserializeInline
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @param identityInfo a {@link IdentityDeserializationInfo} object.
   * @param typeInfo a {@link TypeDeserializationInfo} object.
   * @param typeInformation a {@link String} object.
   * @param typeInformation a {@link String} object.
   * @param bufferedProperties a {@link Map} object.
   * @return a T object.
   */
  T deserializeInline(
      XMLReader reader,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params,
      IdentityDeserializationInfo identityInfo,
      TypeDeserializationInfo typeInfo,
      String typeInformation,
      Map<String, String> bufferedProperties)
      throws XMLStreamException;

  /**
   * deserializeWrapped
   *
   * @param reader a {@link XMLReader} object.
   * @param ctx a {@link XMLDeserializationContext} object.
   * @param params a {@link XMLDeserializerParameters} object.
   * @param identityInfo a {@link IdentityDeserializationInfo} object.
   * @param typeInfo a {@link TypeDeserializationInfo} object.
   * @param typeInformation a {@link String} object.
   * @param typeInformation a {@link String} object.
   * @return a T object.
   */
  T deserializeWrapped(
      XMLReader reader,
      XMLDeserializationContext ctx,
      XMLDeserializerParameters params,
      IdentityDeserializationInfo identityInfo,
      TypeDeserializationInfo typeInfo,
      String typeInformation)
      throws XMLStreamException;
}
