/*
 * Copyright © 2021 Treblereel
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

/** @author Dmitrii Tikhomirov Created by treblereel 7/8/21 */
@J2clTestInput(BooleanArray2dTest.class)
public class BooleanArray2dTest {

  private static final BooleanArray2d_XMLMapperImpl mapper = BooleanArray2d_XMLMapperImpl.INSTANCE;

  private static final String XML0 = "<?xml version='1.0' encoding='UTF-8'?><BooleanArray2d/>";

  private static final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><BooleanArray2d><array><array>true</array><array>false</array><array>true</array><array>false</array></array><array><array>true</array><array>false</array><array>true</array><array>false</array></array><array><array>false</array><array>true</array><array>false</array><array>true</array></array><array><array>true</array><array>false</array><array>true</array><array>false</array></array></BooleanArray2d>";
  private static final String XML2 =
      "<?xml version='1.0' encoding='UTF-8'?><BooleanArray2d><array><array>true</array><array>false</array><array>true</array><array>false</array></array><array><array>true</array><array>false</array><array>true</array><array>false</array></array><array><array>false</array><array>true</array><array>false</array><array>true</array></array><array><array>true</array><array>false</array><array>true</array><array>false</array></array><array2><array2>true</array2><array2>false</array2><array2>true</array2><array2>false</array2></array2><array2><array2>true</array2><array2>false</array2><array2>true</array2><array2>false</array2></array2><array2><array2>false</array2><array2>true</array2><array2>false</array2><array2>true</array2></array2><array2><array2>true</array2><array2>false</array2><array2>true</array2><array2>false</array2></array2></BooleanArray2d>";
  private static final boolean[][] array =
      new boolean[][] {
        {true, false, true, false},
        {true, false, true, false},
        {false, true, false, true},
        {true, false, true, false}
      };

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    BooleanArray2d test = new BooleanArray2d();

    assertEquals(XML0, mapper.write(test));
    assertNull(mapper.read(mapper.write(test)).getArray());
  }

  @Test
  public void testDeserializeValue1() throws XMLStreamException {
    BooleanArray2d test = new BooleanArray2d();

    assertNull(mapper.read(mapper.write(test)).getArray());
    test.setArray(array);

    assertEquals(XML, mapper.write(test));

    boolean[][] result = mapper.read(XML).getArray();
    assertTrue(Arrays.deepEquals(array, result));
  }

  @Test
  public void testDeserializeValue2() throws XMLStreamException {
    BooleanArray2d test = new BooleanArray2d();
    test.setArray(array);
    test.setArray2(array);

    assertEquals(XML2, mapper.write(mapper.read(mapper.write(test))));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray()));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray2()));
  }

  @Test
  public void testSerializeValue() throws XMLStreamException {
    BooleanArray2d test = new BooleanArray2d();
    test.setArray(array);
    assertEquals(XML, mapper.write(test));
    assertTrue(Arrays.deepEquals(array, mapper.read(mapper.write(test)).getArray()));
  }
}
