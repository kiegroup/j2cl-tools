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

package org.kie.j2cl.tools.yaml.tests.primitive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.primitive.CharTest.CharType;

@J2clTestInput(CharTest.class)
public class CharTest extends AbstractYamlTest<CharType> {
  private static final CharTest_CharType_YamlMapperImpl mapper =
      CharTest_CharType_YamlMapperImpl.INSTANCE;

  public CharTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingCharacter() throws IOException {
    CharType charType = new CharType();
    charType.setValue('A');

    assertMarshallingAndUnmarshalling(charType);
  }

  @Test
  public void testMarshallingWhitespaceCharacter() throws IOException {
    CharType charType = new CharType();
    charType.setValue(' ');

    assertMarshallingAndUnmarshalling(charType);
  }

  @Test
  public void testMarshallingSpecialCharacter() throws IOException {
    CharType charType = new CharType();
    charType.setValue('$');

    assertMarshallingAndUnmarshalling(charType);
  }

  @Test
  public void testMarshallingUnicodeCharacter() throws IOException {
    CharType charType = new CharType();
    charType.setValue('\u039A');

    assertMarshallingAndUnmarshalling(charType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    CharType charType1 = new CharType();
    charType1.setValue('A');

    CharType charType2 = new CharType();
    charType2.setValue('A');

    assertEquals(charType1, charType2);
    assertEquals(charType1.hashCode(), charType2.hashCode());

    assertMarshallingAndUnmarshalling(charType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    CharType charType1 = new CharType();
    charType1.setValue('A');

    CharType charType2 = new CharType();
    charType2.setValue('B');

    assertNotEquals(charType1, charType2);

    assertMarshallingAndUnmarshalling(charType1);
    assertMarshallingAndUnmarshalling(charType2);
  }

  @Test
  public void testMarshallingWithNullValue() throws IOException {
    CharType charType = new CharType();
    charType.setValue('\u0000');

    assertMarshallingAndUnmarshalling(charType);
  }

  @YAMLMapper
  public static class CharType {

    private char value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof CharType)) {
        return false;
      }
      CharType intType = (CharType) o;
      return getValue() == intType.getValue();
    }

    public char getValue() {
      return value;
    }

    public void setValue(char value) {
      this.value = value;
    }
  }
}
