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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.array.BooleanArrayTest.BooleanArray;

@J2clTestInput(BooleanArrayTest.class)
public class BooleanArrayTest extends AbstractYamlTest<BooleanArray> {

  private static final BooleanArrayTest_BooleanArray_YamlMapperImpl mapper =
      BooleanArrayTest_BooleanArray_YamlMapperImpl.INSTANCE;

  public BooleanArrayTest() {
    super(mapper);
  }

  @Test
  public void testSerializeValue() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[] {true, false, true, false});
    String yaml = mapper.write(booleanArray);

    assertEquals(
        "values:" + "\n" + "  - true" + "\n" + "  - false" + "\n" + "  - true" + "\n" + "  - false",
        yaml);
  }

  @Test
  public void tesDeSerializeValue() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    boolean[] value = new boolean[] {true, false, true, false};
    booleanArray.setValues(value);
    String yaml = mapper.write(booleanArray);
    assertArrayEquals(value, mapper.read(yaml).getValues());
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(null);

    assertNull(mapper.read(mapper.write(booleanArray)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[0]);

    assertMarshallingAndUnmarshalling(booleanArray);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[] {true});

    assertMarshallingAndUnmarshalling(booleanArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[] {true, false, true});

    assertMarshallingAndUnmarshalling(booleanArray);
  }

  @Test
  public void testMarshallingWithTrueOnlyValues() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[] {true, true, true});

    assertMarshallingAndUnmarshalling(booleanArray);
  }

  @Test
  public void testMarshallingWithFalseOnlyValues() throws IOException {
    BooleanArray booleanArray = new BooleanArray();
    booleanArray.setValues(new boolean[] {false, false, false});

    assertMarshallingAndUnmarshalling(booleanArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    BooleanArray booleanArray1 = new BooleanArray();
    booleanArray1.setValues(new boolean[] {true, false, true});

    BooleanArray booleanArray2 = new BooleanArray();
    booleanArray2.setValues(new boolean[] {true, false, true});

    assertEquals(booleanArray1, booleanArray2);
    assertEquals(booleanArray1.hashCode(), booleanArray2.hashCode());

    assertMarshallingAndUnmarshalling(booleanArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    BooleanArray booleanArray1 = new BooleanArray();
    booleanArray1.setValues(new boolean[] {true, false, true});

    BooleanArray booleanArray2 = new BooleanArray();
    booleanArray2.setValues(new boolean[] {false, true, false});

    assertNotEquals(booleanArray1, booleanArray2);

    assertMarshallingAndUnmarshalling(booleanArray1);
    assertMarshallingAndUnmarshalling(booleanArray2);
  }

  @YAMLMapper
  public static class BooleanArray {

    private boolean[] values;

    public boolean[] getValues() {
      return values;
    }

    public void setValues(boolean[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BooleanArray that = (BooleanArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
