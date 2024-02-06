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
import org.kie.j2cl.tools.yaml.tests.array.LongArrayTest.LongArray;

@J2clTestInput(LongArrayTest.class)
public class LongArrayTest extends AbstractYamlTest<LongArray> {

  private static final LongArrayTest_LongArray_YamlMapperImpl mapper =
      LongArrayTest_LongArray_YamlMapperImpl.INSTANCE;

  private static final long[] values = new long[] {17222, 2111, 32223, -6226};

  public LongArrayTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {
    LongArrayTest.LongArray array = new LongArrayTest.LongArray();
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
    LongArrayTest.LongArray array = new LongArrayTest.LongArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals(array, mapper.read(yaml));
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    LongArray longArray = new LongArray();
    longArray.setValues(null);

    assertNull(mapper.read(mapper.write(longArray)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    LongArray longArray = new LongArray();
    longArray.setValues(new long[0]);

    assertMarshallingAndUnmarshalling(longArray);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    LongArray longArray = new LongArray();
    longArray.setValues(new long[] {1L});

    assertMarshallingAndUnmarshalling(longArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    LongArray longArray = new LongArray();
    longArray.setValues(new long[] {1L, 2L, 3L});

    assertMarshallingAndUnmarshalling(longArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    LongArray longArray1 = new LongArray();
    longArray1.setValues(new long[] {1L, 2L, 3L});

    LongArray longArray2 = new LongArray();
    longArray2.setValues(new long[] {1L, 2L, 3L});

    assertEquals(longArray1, longArray2);
    assertEquals(longArray1.hashCode(), longArray2.hashCode());

    assertMarshallingAndUnmarshalling(longArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    LongArray longArray1 = new LongArray();
    longArray1.setValues(new long[] {1L, 2L, 3L});

    LongArray longArray2 = new LongArray();
    longArray2.setValues(new long[] {3L, 2L, 1L});

    assertNotEquals(longArray1, longArray2);

    assertMarshallingAndUnmarshalling(longArray1);
    assertMarshallingAndUnmarshalling(longArray2);
  }

  @YAMLMapper
  public static class LongArray {

    private long[] values;

    public long[] getValues() {
      return values;
    }

    public void setValues(long[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      LongArray that = (LongArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
