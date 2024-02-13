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
package org.kie.j2cl.tools.xml.mapper.client.tests.arrays.dd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

@J2clTestInput(FloatArray2dTest.class)
public class FloatArray2dTest {

  private final FloatArray2d test = new FloatArray2d();
  private final FloatArray2d_XMLMapperImpl mapper = FloatArray2d_XMLMapperImpl.INSTANCE;
  private final String XML =
      "<?xml version='1.0' encoding='UTF-8'?><FloatArray2d><floats><floats>1.0</floats><floats>2.0</floats><floats>3.0</floats></floats><floats><floats>4.0</floats><floats>5.0</floats><floats>6.0</floats></floats></FloatArray2d>";

  @Test
  public void testDeserializeValue() throws XMLStreamException {
    float[][] floats = new float[][] {{1, 2, 3}, {4, 5, 6}};
    test.setFloats(floats);

    String result = mapper.write(test);

    assertEquals(XML, result);
    assertEquals(XML, mapper.write(test));
    assertEquals(XML, mapper.write(mapper.read(mapper.write(test))));

    assertTrue(Arrays.deepEquals(floats, mapper.read(mapper.write(test)).getFloats()));
  }
}
