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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.array.ByteArrayTest.ByteArray;

@J2clTestInput(ByteArrayTest.class)
public class ByteArrayTest extends AbstractYamlTest<ByteArray> {
  private static final ByteArrayTest_ByteArray_YamlMapperImpl mapper =
      ByteArrayTest_ByteArray_YamlMapperImpl.INSTANCE;

  public ByteArrayTest() {
    super(mapper);
  }

  private static final byte[] values = new byte[] {17, 2, 33, 66};

  @Test
  public void testSerializeValue() throws IOException {
    ByteArrayTest.ByteArray array = new ByteArrayTest.ByteArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals("values: EQIhQg==", yaml);
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    ByteArrayTest.ByteArray array = new ByteArrayTest.ByteArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertArrayEquals(values, mapper.read(yaml).getValues());
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    ByteArray byteArray = new ByteArray();
    byteArray.setValues(null);

    assertNull(mapper.read(mapper.write(byteArray)));
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    ByteArray byteArray = new ByteArray();
    byteArray.setValues(new byte[] {1});

    assertMarshallingAndUnmarshalling(byteArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    ByteArray byteArray = new ByteArray();
    byteArray.setValues(new byte[] {1, 2, 3});

    assertMarshallingAndUnmarshalling(byteArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    ByteArray byteArray1 = new ByteArray();
    byteArray1.setValues(new byte[] {1, 2, 3});

    ByteArray byteArray2 = new ByteArray();
    byteArray2.setValues(new byte[] {1, 2, 3});

    assertEquals(byteArray1, byteArray2);
    assertEquals(byteArray1.hashCode(), byteArray2.hashCode());

    assertMarshallingAndUnmarshalling(byteArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    ByteArray byteArray1 = new ByteArray();
    byteArray1.setValues(new byte[] {1, 2, 3});

    ByteArray byteArray2 = new ByteArray();
    byteArray2.setValues(new byte[] {3, 2, 1});

    assertNotEquals(byteArray1, byteArray2);

    assertMarshallingAndUnmarshalling(byteArray1);
    assertMarshallingAndUnmarshalling(byteArray2);
  }

  @YAMLMapper
  public static class ByteArray {

    private byte[] values;

    public byte[] getValues() {
      return values;
    }

    public void setValues(byte[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ByteArray byteArray = (ByteArray) o;
      return Arrays.equals(values, byteArray.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
