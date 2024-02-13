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
package org.kie.j2cl.tools.xml.mapper.client.tests.collections;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.BoxedSets;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.BoxedSets_XMLMapperImpl;

@J2clTestInput(BoxedSetsTest.class)
public class BoxedSetsTest {

  BoxedSets_XMLMapperImpl mapper = BoxedSets_XMLMapperImpl.INSTANCE;

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    Set<String> strings = Arrays.asList("A", "B", "C", "D").stream().collect(Collectors.toSet());
    Set<Boolean> booleans =
        Arrays.asList(true, true, false, false).stream().collect(Collectors.toSet());
    Set<Character> chars = Arrays.asList('a', 'z', 'F', '!').stream().collect(Collectors.toSet());
    Set<Byte> bytes =
        Arrays.asList((byte) 2, (byte) 12, (byte) 122, (byte) 3).stream()
            .collect(Collectors.toSet());
    Set<Double> doubles =
        Arrays.asList(17222d, 2111d, 32223d, 6226d).stream().collect(Collectors.toSet());
    Set<Integer> ints =
        Arrays.asList(17222, 2111, 32223, 6226).stream().collect(Collectors.toSet());
    Set<Long> longs =
        Arrays.asList(17222l, 2111l, 32223l, 6226l).stream().collect(Collectors.toSet());
    Set<Short> shorts =
        Arrays.asList((short) 3, (short) 13, (short) 123, (short) 233).stream()
            .collect(Collectors.toSet());

    BoxedSets test = new BoxedSets(strings, booleans, chars, bytes, doubles, ints, longs, shorts);

    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
