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

import org.kie.j2cl.tools.xml.mapper.api.utils.DefaultDateFormat;
import org.kie.j2cl.tools.xml.mapper.api.utils.DefaultMapLike;

/**
 * ServerMapperContext class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class ServerMapperContext extends JsMapperContext {

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public MapperContext.DateFormat dateFormat() {
    return new DefaultDateFormat();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public MapLikeFactory mapLikeFactory() {
    return DefaultMapLike::new;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public XMLSerializerParameters defaultSerializerParameters() {
    return ServerMapperXMLSerializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public XMLDeserializerParameters defaultDeserializerParameters() {
    return ServerMapperXMLDeserializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public XMLSerializerParameters newSerializerParameters() {
    return new ServerMapperXMLSerializerParameters();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public XMLDeserializerParameters newDeserializerParameters() {
    return new ServerMapperXMLDeserializerParameters();
  }
}
