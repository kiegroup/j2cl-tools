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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Address;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.Person;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.Users;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.Users_XMLMapperImpl;

/** @author Dmitrii Tikhomirov Created by treblereel 3/30/20 */
@J2clTestInput(BeanMapTest.class)
public class BeanMapTest {

  Users_XMLMapperImpl mapper = Users_XMLMapperImpl.INSTANCE;

  @Test
  public void testStringPersonMap() throws XMLStreamException {
    Map<String, Person> map1 = new LinkedHashMap<>();
    Person p1 = new Person();
    p1.setFirstName("Person1");

    Person p2 = new Person();
    p2.setFirstName("Person2");

    map1.put("_1", p1);
    map1.put("_22", p2);
    map1.put("_333", new Person());
    map1.put("_444", new Person());

    Users test = new Users();
    test.setActiveUsers(map1);

    test.setAllUsers(new ArrayList<>());
    test.getAllUsers().add(p1);
    test.getAllUsers().add(p2);

    Map<Person, Address> personAddressMap = new LinkedHashMap<>();
    personAddressMap.put(p1, new Address(1, "AAA", "BBB"));
    personAddressMap.put(p2, new Address(2, "AAA2", "BBB2"));
    test.setAddressMap(personAddressMap);

    String xml = mapper.write(test);
    Users parsed = mapper.read(xml);
    assertEquals(test, parsed);
    assertEquals(
        mapper.write(test),
        mapper.write(mapper.read(mapper.write(mapper.read(mapper.write(test))))));
  }
}
