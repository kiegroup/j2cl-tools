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

package org.kie.j2cl.tools.xml.mapper.client.tests;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XMLMapper;

@J2clTestInput(RemoveWhitespaceTest.class)
public class RemoveWhitespaceTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?>\n"
          + "<Person>\n"
          + "    <firstName>                               </firstName>\n"
          + "    <lastName>VVV</lastName>\n"
          + "</Person>";

  private final RemoveWhitespaceTest_Person_XMLMapperImpl mapper =
      RemoveWhitespaceTest_Person_XMLMapperImpl.INSTANCE;

  @Test
  public void testSerializeValue() throws XMLStreamException {
    Person test = new Person();
    test.setFirstName("                               ");
    test.setLastName("VVV");
    assertEquals(test, mapper.read(XML));
  }

  @XMLMapper
  public static class Person {

    private String firstName;
    private String lastName;

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Person)) return false;

      Person person = (Person) o;

      if (getFirstName() != null
          ? !getFirstName().equals(person.getFirstName())
          : person.getFirstName() != null) return false;
      return getLastName() != null
          ? getLastName().equals(person.getLastName())
          : person.getLastName() == null;
    }

    @Override
    public int hashCode() {
      int result = getFirstName() != null ? getFirstName().hashCode() : 0;
      result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
      return result;
    }
  }
}
