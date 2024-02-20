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
package org.kie.j2cl.tools.xml.mapper.client.tests.boxed;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.BooleanBean_XMLMapperImpl;

@J2clTestInput(BooleanTest.class)
public class BooleanTest {

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    assertTrue(
        BooleanBean_XMLMapperImpl.INSTANCE
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>true</check></BooleanBean>")
            .getCheck());
    assertFalse(
        BooleanBean_XMLMapperImpl.INSTANCE
            .read(
                "<?xml version='1.0' encoding='UTF-8'?><BooleanBean><check>false</check></BooleanBean>")
            .getCheck());
  }
}