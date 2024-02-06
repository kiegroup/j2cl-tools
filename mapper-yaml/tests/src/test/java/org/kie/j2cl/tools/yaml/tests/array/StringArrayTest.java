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
import org.kie.j2cl.tools.yaml.tests.array.StringArrayTest.StringArray;

@J2clTestInput(StringArrayTest.class)
public class StringArrayTest extends AbstractYamlTest<StringArray> {

  private static final StringArrayTest_StringArray_YamlMapperImpl mapper =
      StringArrayTest_StringArray_YamlMapperImpl.INSTANCE;

  public StringArrayTest() {
    super(mapper);
  }

  @Test
  public void testSingleElementArrayMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {"Hello"});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithMultipleElementsMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {"Hello", "World", "OpenAI"});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testNullArrayMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(null);
    assertEquals("", mapper.write(stringArray));
  }

  @Test
  public void testEmptyArrayMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {});

    assertEquals("values: []", mapper.write(stringArray));

    String nonEmpty = mapper.write(stringArray, doNotWriteEmptyArrays);
    assertEquals("", nonEmpty);
  }

  @Test
  public void testLargeArrayMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    String[] values = new String[1000];
    Arrays.fill(values, "Value");
    stringArray.setValues(values);

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithEmptyStringsMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {"", "", ""});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithWhitespaceStringsMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {" ", "  ", "   "});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithSpecialCharactersMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {"$100", "€200", "@OpenAI"});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithNullStringMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(new String[] {"Hello", null, "World"});

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @Test
  public void testArrayWithUnicodeStringsMarshalling() throws IOException {
    StringArray stringArray = new StringArray();
    stringArray.setValues(
        new String[] {
          "\u039A\u03B1\u03BB\u03B7\u03BC\u03AD\u03C1\u03B5\u03C2", "\u6F22\u5B57", "\uD83D\uDE00"
        });

    assertMarshallingAndUnmarshalling(stringArray);
  }

  @YAMLMapper
  public static class StringArray {

    private String[] values;

    public String[] getValues() {
      return values;
    }

    public void setValues(String[] values) {
      this.values = values;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      StringArray that = (StringArray) o;
      return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(values);
    }
  }
}
