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

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.array.FloatArrayTest.FloatArray;

@J2clTestInput(FloatArrayTest.class)
public class FloatArrayTest extends AbstractYamlTest<FloatArray> {

  private static final FloatArrayTest_FloatArray_YamlMapperImpl mapper =
      FloatArrayTest_FloatArray_YamlMapperImpl.INSTANCE;

  public FloatArrayTest() {
    super(mapper);
  }

  @Test
  public void testNullArrayMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(null);
    assertEquals("", mapper.write(floatArray));
  }

  @Test
  public void testEmptyArrayMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {});

    assertEquals("values: []", mapper.write(floatArray));

    String nonEmpty = mapper.write(floatArray, doNotWriteEmptyArrays);
    assertEquals("", nonEmpty);
  }

  @Test
  public void testSingleElementArrayMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {5.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithMultipleElementsMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {1.0f, 2.0f, 3.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testLargeArrayMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    float[] values = new float[1000];
    Arrays.fill(values, 2.5f);
    floatArray.setValues(values);

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithNegativeValuesMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {-1.0f, -2.0f, -3.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithZeroValuesMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {0.0f, 0.0f, 0.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithNaNValueMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {1.0f, Float.NaN, 3.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithInfinityValueMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {1.0f, Float.POSITIVE_INFINITY, 3.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @Test
  public void testArrayWithNegativeInfinityValueMarshalling() throws IOException {
    FloatArray floatArray = new FloatArray();
    floatArray.setValues(new float[] {1.0f, Float.NEGATIVE_INFINITY, 3.0f});

    assertMarshallingAndUnmarshalling(floatArray);
  }

  @YAMLMapper
  public static class FloatArray {

    private float[] values;

    public float[] getValues() {
      return values;
    }

    public void setValues(float[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      FloatArray that = (FloatArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
