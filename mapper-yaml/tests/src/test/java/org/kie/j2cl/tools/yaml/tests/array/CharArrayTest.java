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
import org.kie.j2cl.tools.yaml.tests.array.CharArrayTest.CharArray;

@J2clTestInput(CharArrayTest.class)
public class CharArrayTest extends AbstractYamlTest<CharArray> {
  private static final CharArrayTest_CharArray_YamlMapperImpl mapper =
      CharArrayTest_CharArray_YamlMapperImpl.INSTANCE;

  public CharArrayTest() {
    super(mapper);
  }

  private static final char[] values = new char[] {'a', 'z', 'F', '!'};

  private final String YAML =
      "values:" + "\n" + "  - a" + "\n" + "  - z" + "\n" + "  - F" + "\n" + "  - '!'";

  @Test
  public void testSerializeValue() throws IOException {
    CharArrayTest.CharArray array = new CharArrayTest.CharArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertEquals(YAML, yaml);
    assertEquals(array, mapper.read(yaml));
  }

  @Test
  public void testDeSerializeValue() throws IOException {
    CharArrayTest.CharArray array = new CharArrayTest.CharArray();
    array.setValues(values);
    String yaml = mapper.write(array);
    assertArrayEquals(values, mapper.read(yaml).getValues());
  }

  @Test
  public void testMarshallingWithNullValues() throws IOException {
    CharArray charArray = new CharArray();
    charArray.setValues(null);

    assertNull(mapper.read(mapper.write(charArray)));
  }

  @Test
  public void testMarshallingWithEmptyValues() throws IOException {
    CharArray charArray = new CharArray();
    charArray.setValues(new char[0]);

    assertMarshallingAndUnmarshalling(charArray);
  }

  @Test
  public void testMarshallingWithSingleValue() throws IOException {
    CharArray charArray = new CharArray();
    charArray.setValues(new char[] {'a'});

    assertMarshallingAndUnmarshalling(charArray);
  }

  @Test
  public void testMarshallingWithMultipleValues() throws IOException {
    CharArray charArray = new CharArray();
    charArray.setValues(new char[] {'a', 'b', 'c'});

    assertMarshallingAndUnmarshalling(charArray);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    CharArray charArray1 = new CharArray();
    charArray1.setValues(new char[] {'a', 'b', 'c'});

    CharArray charArray2 = new CharArray();
    charArray2.setValues(new char[] {'a', 'b', 'c'});

    assertEquals(charArray1, charArray2);
    assertEquals(charArray1.hashCode(), charArray2.hashCode());

    assertMarshallingAndUnmarshalling(charArray1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    CharArray charArray1 = new CharArray();
    charArray1.setValues(new char[] {'a', 'b', 'c'});

    CharArray charArray2 = new CharArray();
    charArray2.setValues(new char[] {'c', 'b', 'a'});

    assertNotEquals(charArray1, charArray2);

    assertMarshallingAndUnmarshalling(charArray1);
    assertMarshallingAndUnmarshalling(charArray2);
  }

  @YAMLMapper
  public static class CharArray {

    private char[] values;

    public char[] getValues() {
      return values;
    }

    public void setValues(char[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CharArray charArray = (CharArray) o;
      return Arrays.equals(values, charArray.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
