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

import static java.util.Objects.isNull;

/**
 * MapperContextProvider class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class MapperContextProvider {

  static MapperContext context;

  /**
   * get.
   *
   * @return a {@link MapperContext} object.
   */
  public static MapperContext get() {
    if (isNull(context)) initContext();
    return context;
  }

  private static void initContext() {
    context = new ServerMapperContext();
  }
}
