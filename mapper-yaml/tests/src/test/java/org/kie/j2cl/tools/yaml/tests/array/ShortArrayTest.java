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

package org.kie.j2cl.tools.yaml.tests.array;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.array.ShortArrayTest.ShortArray;

@J2clTestInput(ShortArrayTest.class)
public class ShortArrayTest extends AbstractYamlTest<ShortArray> {

  private static final ShortArrayTest_ShortArray_YamlMapperImpl mapper =
      ShortArrayTest_ShortArray_YamlMapperImpl.INSTANCE;

  public ShortArrayTest() {
    super(mapper);
  }

  private static final short[] values = new short[] {17222, 2111, 32223, -6226};

  @Test
  public void testSerializeValue() throws IOException {
    ShortArrayTest.ShortArray array = new ShortArrayTest.ShortArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals(
        "values:"
            + "\n"
            + "  - 17222"
            + "\n"
            + "  - 2111"
            + "\n"
            + "  - 32223"
            + "\n"
            + "  - -6226",
        yaml);
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    ShortArrayTest.ShortArray array = new ShortArrayTest.ShortArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals(array, mapper.read(yaml));
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    ShortArray shortArray = new ShortArray();
    shortArray.setValues(null);

    assertNull(mapper.read(mapper.write(shortArray)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    ShortArray shortArray = new ShortArray();
    shortArray.setValues(new short[0]);

    assertMarshallingAndUnmarshalling(shortArray);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    ShortArray shortArray = new ShortArray();
    shortArray.setValues(new short[] {1});

    assertMarshallingAndUnmarshalling(shortArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    ShortArray shortArray = new ShortArray();
    shortArray.setValues(new short[] {1, 2, 3});

    assertMarshallingAndUnmarshalling(shortArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    ShortArray shortArray1 = new ShortArray();
    shortArray1.setValues(new short[] {1, 2, 3});

    ShortArray shortArray2 = new ShortArray();
    shortArray2.setValues(new short[] {1, 2, 3});

    assertEquals(shortArray1, shortArray2);
    assertEquals(shortArray1.hashCode(), shortArray2.hashCode());

    assertMarshallingAndUnmarshalling(shortArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    ShortArray shortArray1 = new ShortArray();
    shortArray1.setValues(new short[] {1, 2, 3});

    ShortArray shortArray2 = new ShortArray();
    shortArray2.setValues(new short[] {3, 2, 1});

    assertNotEquals(shortArray1, shortArray2);

    assertMarshallingAndUnmarshalling(shortArray1);
    assertMarshallingAndUnmarshalling(shortArray2);
  }

  @YAMLMapper
  public static class ShortArray {

    private short[] values;

    public short[] getValues() {
      return values;
    }

    public void setValues(short[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ShortArray that = (ShortArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
