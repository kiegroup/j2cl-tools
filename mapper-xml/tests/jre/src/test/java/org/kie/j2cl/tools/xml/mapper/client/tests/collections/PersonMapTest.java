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

package org.kie.j2cl.tools.xml.mapper.client.tests.collections;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Person;

/** @author Dmitrii Tikhomirov Created by treblereel 7/31/20 */
@J2clTestInput(PersonMapTest.class)
public class PersonMapTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><PersonMap><map><entry><key><firstName>setFirstName</firstName><lastName>setLastName</lastName><birthday>1</birthday><alive>1</alive></key><value><firstName>setFirstName</firstName><lastName>setLastName</lastName><birthday>1</birthday><alive>1</alive></value></entry></map></PersonMap>";
  PersonMap_XMLMapperImpl mapper = PersonMap_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    PersonMap personMap = new PersonMap();

    Person person = new Person();
    person.setBirthday(new Date(1));
    person.setFirstName("setFirstName");
    person.setLastName("setLastName");
    Map<Person, List<Person>> map = new LinkedHashMap<>();
    List<Person> list = new LinkedList<>();
    list.add(person);
    map.put(person, list);
    personMap.setMap(map);

    String result = mapper.write(personMap);

    assertEquals(XML, result);
    assertEquals(personMap, mapper.read(mapper.write(personMap)));
    assertEquals(person, mapper.read(mapper.write(personMap)).getMap().get(person).get(0));
  }
}
