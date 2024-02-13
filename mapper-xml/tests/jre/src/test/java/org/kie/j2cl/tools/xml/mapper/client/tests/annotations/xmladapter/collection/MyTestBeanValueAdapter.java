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

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class MyTestBeanValueAdapter extends XmlAdapter<MyCustomBean, GenericBean> {

  @Override
  public MyCustomBean marshal(GenericBean v) throws Exception {
    MyCustomBean bean = new MyCustomBean();

    if (v instanceof BeanOneGenericBean) {
      bean.setZzz(((BeanOneGenericBean) v).getValue());
      bean.setType(new BeanOneGenericBeanType());

    } else if (v instanceof BeanTwoGenericBean) {
      bean.setZzz(((BeanTwoGenericBean) v).getId());
      bean.setType(new BeanTwoGenericBeanType());

    } else if (v instanceof BeanThreeGenericBean) {
      bean.setZzz(((BeanThreeGenericBean) v).getName());
      bean.setType(new BeanThreeGenericBeanType());
    }
    return bean;
  }

  @Override
  public GenericBean unmarshal(MyCustomBean v) throws Exception {
    GenericBean genericBean;
    if (v.getType() instanceof BeanOneGenericBeanType) {
      genericBean = new BeanOneGenericBean();
      ((BeanOneGenericBean) genericBean).setValue(v.getZzz());
    } else if (v.getType() instanceof BeanTwoGenericBeanType) {
      genericBean = new BeanTwoGenericBean();
      ((BeanTwoGenericBean) genericBean).setId(v.getZzz());
    } else if (v.getType() instanceof BeanThreeGenericBeanType) {
      genericBean = new BeanThreeGenericBean();
      ((BeanThreeGenericBean) genericBean).setName(v.getZzz());
    } else {
      throw new Error("Unable to determine the type of the Bean");
    }
    return genericBean;
  }
}
