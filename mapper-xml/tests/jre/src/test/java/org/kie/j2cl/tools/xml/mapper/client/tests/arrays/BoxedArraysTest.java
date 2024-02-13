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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(BoxedArraysTest.class)
public class BoxedArraysTest {

  BoxedArrays_XMLMapperImpl mapper = BoxedArrays_XMLMapperImpl.INSTANCE;

  String[] strings = new String[] {"AAA", "ZZZ", "1111"};
  Boolean[] booleans = new Boolean[] {true, true, false, false};
  Character[] chars = new Character[] {'a', 'z', 'F', '!'};
  Byte[] bytes = new Byte[] {17, 2, 33, 66};
  Double[] doubles = new Double[] {17222.1d, 2111.2d, 32223.3d, 6226.4d};
  Integer[] ints = new Integer[] {17222, 2111, 32223, 6226};
  Long[] longs = new Long[] {17222l, 2111l, 32223l, 6226l};
  Short[] shorts = new Short[] {17222, 2111, 32223, 6226};

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    BoxedArrays test =
        new BoxedArrays(strings, booleans, chars, bytes, doubles, ints, longs, shorts);

    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><BoxedArrays><strings>AAA</strings><strings>ZZZ</strings><strings>1111</strings><booleans>true</booleans><booleans>true</booleans><booleans>false</booleans><booleans>false</booleans><chars>a</chars><chars>z</chars><chars>F</chars><chars>!</chars><bytes>17</bytes><bytes>2</bytes><bytes>33</bytes><bytes>66</bytes><doubles>17222.1</doubles><doubles>2111.2</doubles><doubles>32223.3</doubles><doubles>6226.4</doubles><ints>17222</ints><ints>2111</ints><ints>32223</ints><ints>6226</ints><longs>17222</longs><longs>2111</longs><longs>32223</longs><longs>6226</longs><shorts>17222</shorts><shorts>2111</shorts><shorts>32223</shorts><shorts>6226</shorts></BoxedArrays>",
        mapper.write(test));

    assertEquals(test, mapper.read(mapper.write(test)));
  }
}
