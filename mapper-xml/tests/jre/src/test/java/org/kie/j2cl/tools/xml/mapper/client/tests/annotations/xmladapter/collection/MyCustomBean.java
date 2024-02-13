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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.collection;

import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@XMLMapper
public class MyCustomBean {

  @XmlElementRefs({
    @XmlElementRef(name = "_typeOne", type = BeanOneGenericBeanType.class),
    @XmlElementRef(name = "_typeTwo", type = BeanTwoGenericBeanType.class),
    @XmlElementRef(name = "_typeThree", type = BeanThreeGenericBeanType.class)
  })
  private BeanType type;

  private String zzz;

  public BeanType getType() {
    return type;
  }

  public void setType(BeanType type) {
    this.type = type;
  }

  public String getZzz() {
    return zzz;
  }

  public void setZzz(String zzz) {
    this.zzz = zzz;
  }
}
