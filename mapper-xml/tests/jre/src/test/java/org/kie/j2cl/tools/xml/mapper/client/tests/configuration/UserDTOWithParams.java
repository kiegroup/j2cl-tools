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

package org.kie.j2cl.tools.xml.mapper.client.tests.configuration;

import org.kie.j2cl.tools.xml.mapper.api.annotation.Configuration;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@Configuration(
    additionalAnnotation = {
      @Configuration.ConfigurationAnnotation(
          value = Deprecated.class,
          params = {
            @Configuration.ConfigurationAnnotationParam(key = "since", value = "1981"),
            @Configuration.ConfigurationAnnotationParam(key = "forRemoval", value = "true")
          }),
      @Configuration.ConfigurationAnnotation(
          value = CustomAnnotation.class,
          params = {
            @Configuration.ConfigurationAnnotationParam(key = "p1", value = "127"),
            @Configuration.ConfigurationAnnotationParam(key = "p2", value = "1981"),
            @Configuration.ConfigurationAnnotationParam(key = "p3", value = "'c'"),
            @Configuration.ConfigurationAnnotationParam(key = "p4", value = "11.11d"),
            @Configuration.ConfigurationAnnotationParam(key = "p5", value = "1981l"),
            @Configuration.ConfigurationAnnotationParam(key = "p6", value = "String.class"),
            @Configuration.ConfigurationAnnotationParam(key = "p7", value = "String.class"),
            @Configuration.ConfigurationAnnotationParam(key = "p8", value = "false"),
          })
    })
@XMLMapper
public class UserDTOWithParams {

  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
