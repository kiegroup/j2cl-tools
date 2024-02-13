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
package org.kie.j2cl.tools.xml.mapper.client.tests.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.TransientBean;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.TransientBean_XMLMapperImpl;

@J2clTestInput(TransientBeanTest.class)
public class TransientBeanTest {

  TransientBean_XMLMapperImpl mapper = TransientBean_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    TransientBean test = new TransientBean();
    test.setDontSaveMe("NOPE");
    test.setDontSaveMeToo("NOPE again");
    test.setSaveMe("YEAP");
    assertEquals(TransientBean.XML, mapper.write(test));
    assertEquals(test.getSaveMe(), mapper.read(mapper.write(test)).getSaveMe());
    assertNull(test.getSaveMe(), mapper.read(mapper.write(test)).getDontSaveMe());
    assertNull(test.getDontSaveMeToo(), mapper.read(mapper.write(test)).getDontSaveMe());
  }
}
