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

@J2clTestInput(CharacterTest.class)
public class CharacterTest extends AbstractYamlTest<CharacterTest.CharacterBean> {

  private static final CharacterTest_CharacterBean_YamlMapperImpl mapper =
      CharacterTest_CharacterBean_YamlMapperImpl.INSTANCE;

  public CharacterTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingLowercaseCharacter() throws IOException {
    CharacterBean characterBean = new CharacterBean();
    characterBean.setCharVal('a');

    assertMarshallingAndUnmarshalling(characterBean);
  }

  @Test
  public void testMarshallingUppercaseCharacter() throws IOException {
    CharacterBean characterBean = new CharacterBean();
    characterBean.setCharVal('Z');

    assertMarshallingAndUnmarshalling(characterBean);
  }

  @Test
  public void testMarshallingWhitespaceCharacter() throws IOException {
    CharacterBean characterBean = new CharacterBean();
    characterBean.setCharVal(' ');

    assertMarshallingAndUnmarshalling(characterBean);
  }

  @Test
  public void testMarshallingSpecialCharacter() throws IOException {
    CharacterBean characterBean = new CharacterBean();
    characterBean.setCharVal('$');

    assertMarshallingAndUnmarshalling(characterBean);
  }

  @Test
  public void testMarshallingUnicodeCharacter() throws IOException {
    CharacterBean characterBean = new CharacterBean();
    characterBean.setCharVal('\u039A');

    assertMarshallingAndUnmarshalling(characterBean);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    CharacterBean characterBean1 = new CharacterBean();
    characterBean1.setCharVal('A');

    CharacterBean characterBean2 = new CharacterBean();
    characterBean2.setCharVal('A');

    assertEquals(characterBean1, characterBean2);
    assertEquals(characterBean1.hashCode(), characterBean2.hashCode());

    assertMarshallingAndUnmarshalling(characterBean1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    CharacterBean characterBean1 = new CharacterBean();
    characterBean1.setCharVal('A');

    CharacterBean characterBean2 = new CharacterBean();
    characterBean2.setCharVal('B');

    assertNotEquals(characterBean1, characterBean2);

    assertMarshallingAndUnmarshalling(characterBean1);
    assertMarshallingAndUnmarshalling(characterBean2);
  }

  @YAMLMapper
  public static class CharacterBean {

    private char charVal;

    public char getCharVal() {
      return charVal;
    }

    public void setCharVal(char charVal) {
      this.charVal = charVal;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof CharacterBean)) {
        return false;
      }
      CharacterBean that = (CharacterBean) o;
      return getCharVal() == that.getCharVal();
    }

    @Override
    public int hashCode() {
      return Objects.hash(getCharVal());
    }
  }
}
