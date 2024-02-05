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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays.dd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@J2clTestInput(ByteArray2dTest.class)
public class ByteArray2dTest {

  private static final ByteArray2d_XMLMapperImpl mapper = ByteArray2d_XMLMapperImpl.INSTANCE;

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><ByteArray2d><array>AAsWIQ==</array><array>APXq3w==</array><array>AGScAA==</array><array>AAAAAA==</array></ByteArray2d>";

  private static final String XML2 =
      "<?xml version='1.0' encoding='UTF-8'?><ByteArray2d><array>AAsWIQ==</array><array>APXq3w==</array><array>AGScAA==</array><array>AAAAAA==</array><array2>AAsWIQ==</array2><array2>APXq3w==</array2><array2>AGScAA==</array2><array2>AAAAAA==</array2></ByteArray2d>";

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    byte[][] array =
        new byte[][] {{0, 11, 22, 33}, {0, -11, -22, -33}, {0, 100, -100, 0}, {0, 0, 0, 0}};

    ByteArray2d test = new ByteArray2d();

    assertNull(mapper.read(mapper.write(test)).getArray());
    test.setArray(array);

    byte[][] result = mapper.read(XML).getArray();
    assertTrue(Arrays.deepEquals(array, result));

    test.setArray2(array);

    assertEquals(XML2, mapper.write(mapper.read(mapper.write(test))));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray()));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray2()));
  }

  @Test
  public void testSerializeValue() throws XMLStreamException {
    byte[][] array =
        new byte[][] {{0, 11, 22, 33}, {0, -11, -22, -33}, {0, 100, -100, 0}, {0, 0, 0, 0}};
    ByteArray2d test = new ByteArray2d();
    test.setArray(array);
    assertEquals(XML, mapper.write(test));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray()));
  }
}
