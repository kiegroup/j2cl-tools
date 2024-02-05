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

package org.kie.j2cl.tools.xml.mapper.client.tests.beans;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.DefaultXMLDeserializationContext;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@J2clTestInput(FailOnUnknownPropertiesTest.class)
public class FailOnUnknownPropertiesTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><Bean><value>BEAN</value><value2>BEAN</value2></Bean>";

  FailOnUnknownPropertiesTest_Bean_XMLMapperImpl mapper =
      FailOnUnknownPropertiesTest_Bean_XMLMapperImpl.INSTANCE;

  @Test
  public void testDoNotFail() throws XMLStreamException {
    Bean bean = new Bean();
    bean.setValue("BEAN");

    assertEquals(
        bean,
        mapper.read(
            XML,
            DefaultXMLDeserializationContext.builder().failOnUnknownProperties(false).build()));
  }

  @XMLMapper
  public static class Bean {
    private String value;

    @Override
    public int hashCode() {
      return getValue() != null ? getValue().hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Bean)) return false;

      Bean bean = (Bean) o;

      return getValue() != null ? getValue().equals(bean.getValue()) : bean.getValue() == null;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
