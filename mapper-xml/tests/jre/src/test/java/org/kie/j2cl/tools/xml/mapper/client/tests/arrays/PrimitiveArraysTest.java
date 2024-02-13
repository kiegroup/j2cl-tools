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

@J2clTestInput(PrimitiveArraysTest.class)
public class PrimitiveArraysTest {

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><PrimitiveArrays><strings>AAA</strings><strings>ZZZ</strings><strings>1111</strings><booleans>true</booleans><booleans>true</booleans><booleans>false</booleans><booleans>false</booleans><chars>a</chars><chars>z</chars><chars>F</chars><chars>!</chars><bytes>EQIhQg==</bytes><doubles>17222.01</doubles><doubles>2111.34</doubles><doubles>32223.34</doubles><doubles>6226.37</doubles><ints>17222</ints><ints>2111</ints><ints>32223</ints><ints>6226</ints><longs>17222</longs><longs>2111</longs><longs>32223</longs><longs>6226</longs><shorts>17222</shorts><shorts>2111</shorts><shorts>32223</shorts><shorts>6226</shorts></PrimitiveArrays>";

  String[] strings = new String[] {"AAA", "ZZZ", "1111"};
  boolean[] booleans = new boolean[] {true, true, false, false};
  char[] chars = new char[] {'a', 'z', 'F', '!'};
  byte[] bytes = new byte[] {17, 2, 33, 66};
  double[] doubles = new double[] {17222.01, 2111.34, 32223.34, 6226.37};
  int[] ints = new int[] {17222, 2111, 32223, 6226};
  long[] longs = new long[] {17222, 2111, 32223, 6226};
  short[] shorts = new short[] {17222, 2111, 32223, 6226};

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    PrimitiveArrays_XMLMapperImpl mapper = PrimitiveArrays_XMLMapperImpl.INSTANCE;

    PrimitiveArrays test =
        new PrimitiveArrays(strings, booleans, chars, bytes, doubles, ints, longs, shorts);
    assertEquals(XML, mapper.write(test));
    assertEquals(XML, mapper.write(mapper.read(mapper.write(test))));
    for (int i = 0; i < 10; i++) {
      assertEquals(test, mapper.read(mapper.write(test)));
    }
  }

  @Test
  public void testDeserializeValueUnwrapped() throws XMLStreamException {
    PrimitiveArraysUnwrapped_XMLMapperImpl mapper = PrimitiveArraysUnwrapped_XMLMapperImpl.INSTANCE;

    PrimitiveArraysUnwrapped test =
        new PrimitiveArraysUnwrapped(strings, booleans, chars, bytes, doubles, ints, longs, shorts);
    for (int i = 0; i < 10; i++) {
      assertEquals(test, mapper.read(mapper.write(test)));
    }
  }
}
