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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.handler;

import javax.xml.stream.XMLStreamException;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializerParameters;
import org.kie.j2cl.tools.xml.mapper.api.custom.CustomXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;

/** @author Dmitrii Tikhomirov Created by treblereel 5/19/20 */
public class MyBeanDemarshaller extends CustomXMLDeserializer<MyBean> {

  @Override
  protected MyBean doDeserialize(
      XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLStreamException {
    return parse(reader.nextString());
  }

  private MyBean parse(String value) {
    MyBean myBean = new MyBean();
    myBean.setValue(value.split("\\+")[0]);
    myBean.setValue2(value.split("\\+")[1]);
    return myBean;
  }

  @Override
  public MyBean deserialize(
      String value, XMLDeserializationContext ctx, XMLDeserializerParameters params)
      throws XMLDeserializationException {
    return parse(value);
  }
}
