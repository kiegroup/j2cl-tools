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
package org.kie.j2cl.tools.xml.mapper.api.stream;

/**
 * Stack interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface Stack<T> {
  /**
   * setAt.
   *
   * @param index a int.
   * @param value a T object.
   */
  void setAt(int index, T value);

  /**
   * getAt.
   *
   * @param index a int.
   * @return a T object.
   */
  T getAt(int index);
}
