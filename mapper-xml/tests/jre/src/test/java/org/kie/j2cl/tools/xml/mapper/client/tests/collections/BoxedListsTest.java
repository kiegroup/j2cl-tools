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
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.BoxedLists;
import org.kie.j2cl.tools.xml.mapper.client.tests.beans.collection.BoxedLists_XMLMapperImpl;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@J2clTestInput(BoxedListsTest.class)
public class BoxedListsTest {

  BoxedLists_XMLMapperImpl mapper = BoxedLists_XMLMapperImpl.INSTANCE;

  private List<String> strings = Arrays.asList("A", "B", "C", "D");
  private List<Boolean> booleans = Arrays.asList(true, true, false, false);
  private List<Character> chars = Arrays.asList('a', 'z', 'F', '!');
  private List<Byte> bytes = Arrays.asList((byte) 2, (byte) 12, (byte) 122, (byte) 3);
  private List<Double> doubles = Arrays.asList(17222.1d, 2111.2d, 32223.3d, 6226.3d);
  private List<Integer> ints = Arrays.asList(17222, 2111, 32223, 6226);
  private List<Long> longs = Arrays.asList(17222l, 2111l, 32223l, 6226l);
  private List<Short> shorts = Arrays.asList((short) 3, (short) 13, (short) 123, (short) 233);

  @Test
  public void testWrapped() throws XMLStreamException {
    BoxedLists test = new BoxedLists(strings, booleans, chars, bytes, doubles, ints, longs, shorts);
    String xml =
        "<?xml version='1.0' encoding='UTF-8'?><BoxedLists><strings>A</strings><strings>B</strings><strings>C</strings><strings>D</strings><booleans>true</booleans><booleans>true</booleans><booleans>false</booleans><booleans>false</booleans><chars>a</chars><chars>z</chars><chars>F</chars><chars>!</chars><bytes>2</bytes><bytes>12</bytes><bytes>122</bytes><bytes>3</bytes><doubles>17222.1</doubles><doubles>2111.2</doubles><doubles>32223.3</doubles><doubles>6226.3</doubles><ints>17222</ints><ints>2111</ints><ints>32223</ints><ints>6226</ints><longs>17222</longs><longs>2111</longs><longs>32223</longs><longs>6226</longs><shorts>3</shorts><shorts>13</shorts><shorts>123</shorts><shorts>233</shorts></BoxedLists>";

    assertEquals(xml, mapper.write(test));
    assertEquals(xml, mapper.write(mapper.read(mapper.write(test))));
    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
