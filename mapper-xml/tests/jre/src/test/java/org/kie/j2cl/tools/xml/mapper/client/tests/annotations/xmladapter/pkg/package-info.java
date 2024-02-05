/*
 * Copyright © 2020 Treblereel
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
/** @author Dmitrii Tikhomirov Created by treblereel 4/5/20 */
@XmlJavaTypeAdapters({
  @XmlJavaTypeAdapter(value = MyTestBeanValueAdapter.class, type = MyCustomBean.class),
  @XmlJavaTypeAdapter(value = MyTestBeanValueAdapter.class, type = MyCustomBean2.class),
})
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.pkg;

import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.MyCustomBean;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.MyCustomBean2;
import org.kie.j2cl.tools.xml.mapper.client.tests.annotations.xmladapter.MyTestBeanValueAdapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
