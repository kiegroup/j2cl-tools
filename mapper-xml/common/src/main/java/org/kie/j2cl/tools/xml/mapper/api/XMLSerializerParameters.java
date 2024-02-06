/*
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
package org.kie.j2cl.tools.xml.mapper.api;

import java.util.Set;
import org.kie.j2cl.tools.xml.mapper.api.ser.bean.TypeSerializationInfo;

/**
 * XMLSerializerParameters interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface XMLSerializerParameters {
  /**
   * getPattern.
   *
   * @return a {@link java.lang.String} object.
   */
  String getPattern();

  /**
   * setPattern.
   *
   * @param pattern a {@link java.lang.String} object.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters setPattern(String pattern);

  /**
   * getLocale.
   *
   * @return a {@link java.lang.String} object.
   */
  String getLocale();

  /**
   * setLocale.
   *
   * @param locale a {@link java.lang.String} object.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters setLocale(String locale);

  /**
   * getTimezone.
   *
   * @return a {@link java.lang.Object} object.
   */
  Object getTimezone();

  /**
   * setTimezone.
   *
   * @param timezone a {@link java.lang.Object} object.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters setTimezone(Object timezone);

  /**
   * getIgnoredProperties.
   *
   * @return a {@link java.util.Set} object.
   */
  Set<String> getIgnoredProperties();

  /**
   * addIgnoredProperty.
   *
   * @param ignoredProperty a {@link java.lang.String} object.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters addIgnoredProperty(String ignoredProperty);

  /**
   * getTypeInfo.
   *
   * @return a {@link TypeSerializationInfo} object.
   */
  TypeSerializationInfo getTypeInfo();

  /**
   * setTypeInfo.
   *
   * @param typeInfo a {@link TypeSerializationInfo} object.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo);

  /**
   * isUnwrapped.
   *
   * @return a boolean.
   */
  boolean isUnwrapped();

  /**
   * setUnwrapped.
   *
   * @param unwrapped a boolean.
   * @return a {@link XMLSerializerParameters} object.
   */
  XMLSerializerParameters setUnwrapped(boolean unwrapped);

  String doubleValue(Double value);
}
