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

import com.google.j2cl.junit.apt.J2clTestInput;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 3/29/20 */
@J2clTestInput(LongArray2dTest.class)
public class LongArray2dTest {

  private final LongArray2d test = new LongArray2d();
  private final LongArray2d_XMLMapperImpl mapper = LongArray2d_XMLMapperImpl.INSTANCE;
  private final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><LongArray2d><array><array>1</array><array>2</array><array>3</array><array>4</array><array>5</array><array>6</array></array></LongArray2d>";
  private final String XML2 =
      "<?xml version='1.0' encoding='UTF-8'?><LongArray2d><array><array>1</array><array>2</array><array>3</array><array>4</array><array>5</array><array>6</array></array><array2><array2>1</array2><array2>2</array2><array2>3</array2><array2>4</array2><array2>5</array2><array2>6</array2></array2></LongArray2d>";

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    long[][] arrays = new long[][] {{1, 2, 3}, {4, 5, 6}};
    test.setArray(arrays);

    String result = mapper.write(test);
    assertEquals(XML, result);
    assertEquals(XML, mapper.write(test));
    assertEquals(XML, mapper.write(mapper.read(mapper.write(test))));
  }

  @Test
  public void testDeserializeValue1() throws XMLStreamException {
    long[][] arrays = new long[][] {{1, 2, 3}, {4, 5, 6}};
    test.setArray(arrays);
    test.setArray2(arrays);

    String result = mapper.write(test);
    assertEquals(XML2, result);
    assertEquals(XML2, mapper.write(test));
    assertEquals(XML2, mapper.write(mapper.read(mapper.write(test))));
  }
}
