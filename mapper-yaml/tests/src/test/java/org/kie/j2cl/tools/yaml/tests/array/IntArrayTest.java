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
import org.kie.j2cl.tools.yaml.tests.array.IntArrayTest.IntArray;

@J2clTestInput(IntArrayTest.class)
public class IntArrayTest extends AbstractYamlTest<IntArray> {

  private static final IntArrayTest_IntArray_YamlMapperImpl mapper =
      IntArrayTest_IntArray_YamlMapperImpl.INSTANCE;

  public IntArrayTest() {
    super(mapper);
  }

  private static final int[] values = new int[] {17222, 2111, 32223, -6226};

  @Test
  public void testSerializeValue() throws IOException {
    IntArrayTest.IntArray array = new IntArrayTest.IntArray();
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
    IntArrayTest.IntArray array = new IntArrayTest.IntArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals(array, mapper.read(yaml));
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    IntArray intArray = new IntArray();
    intArray.setValues(null);

    assertNull(mapper.read(mapper.write(intArray)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    IntArray intArray = new IntArray();
    intArray.setValues(new int[0]);

    assertMarshallingAndUnmarshalling(intArray);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    IntArray intArray = new IntArray();
    intArray.setValues(new int[] {1});

    assertMarshallingAndUnmarshalling(intArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    IntArray intArray = new IntArray();
    intArray.setValues(new int[] {1, 2, 3});

    assertMarshallingAndUnmarshalling(intArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    IntArray intArray1 = new IntArray();
    intArray1.setValues(new int[] {1, 2, 3});

    IntArray intArray2 = new IntArray();
    intArray2.setValues(new int[] {1, 2, 3});

    assertEquals(intArray1, intArray2);
    assertEquals(intArray1.hashCode(), intArray2.hashCode());

    assertMarshallingAndUnmarshalling(intArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    IntArray intArray1 = new IntArray();
    intArray1.setValues(new int[] {1, 2, 3});

    IntArray intArray2 = new IntArray();
    intArray2.setValues(new int[] {3, 2, 1});

    assertNotEquals(intArray1, intArray2);

    assertMarshallingAndUnmarshalling(intArray1);
    assertMarshallingAndUnmarshalling(intArray2);
  }

  @YAMLMapper
  public static class IntArray {

    private int[] values;

    public int[] getValues() {
      return values;
    }

    public void setValues(int[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      IntArray that = (IntArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
