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

package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class MyTestBeanValueAdapter extends XmlAdapter<MyCustomBeanType, MyCustomBean> {

  @Override
  public MyCustomBean unmarshal(MyCustomBeanType v) throws Exception {
    return new MyCustomBean(v);
  }

  @Override
  public MyCustomBeanType marshal(MyCustomBean v) throws Exception {
    return new MyCustomBeanType(v);
  }
}
