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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations.collection;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 4/6/20 */
@J2clTestInput(DataHolderTest.class)
public class DataHolderTest {

  DataHolder_XMLMapperImpl mapperEmployee = DataHolder_XMLMapperImpl.INSTANCE;

  @Test
  public void testDataHolder() throws XMLStreamException {
    DataHolder holder = new DataHolder();
    holder.addDataHolder(new Data("AAA"));
    holder.addDataHolder(new Data("BBB"));
    holder.addDataHolder(new Data("CCC"));

    holder.addDataHolder(new Data("CCC1"));
    holder.addDataHolder(new Data("CCC2"));
    holder.addDataHolder(new Data("CCC3"));
    holder.addDataHolder(new Data("CCC4"));
    holder.addDataHolder(new Data("CCC5"));

    holder.getData2().addDataHolder(new Data("QQQ"));
    holder.getData2().addDataHolder(new Data("WWW"));
    holder.getData2().addDataHolder(new Data("EEE"));

    String xml = mapperEmployee.write(holder);
    assertEquals(holder, mapperEmployee.read(xml));
  }
}
