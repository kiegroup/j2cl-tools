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

/**
 * Contains identity informations for deserialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public interface IdentityDeserializationInfo {

  /**
   * getPropertyName
   *
   * @return name of the identifier property
   */
  String getPropertyName();

  /**
   * isProperty
   *
   * @return true if the identifier is also a property of the bean
   */
  boolean isProperty();
}
