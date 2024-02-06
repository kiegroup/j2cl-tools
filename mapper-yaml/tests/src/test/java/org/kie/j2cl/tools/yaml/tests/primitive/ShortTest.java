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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.primitive.ShortTest.ShortType;

@J2clTestInput(ShortTest.class)
public class ShortTest extends AbstractYamlTest<ShortType> {

  private static final ShortTest_ShortType_YamlMapperImpl mapper =
      ShortTest_ShortType_YamlMapperImpl.INSTANCE;

  public ShortTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck((short) 42);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck((short) -10);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    ShortType shortType1 = new ShortType();
    shortType1.setCheck((short) 123);

    ShortType shortType2 = new ShortType();
    shortType2.setCheck((short) 123);

    assertEquals(shortType1, shortType2);
    assertEquals(shortType1.hashCode(), shortType2.hashCode());

    assertMarshallingAndUnmarshalling(shortType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    ShortType shortType1 = new ShortType();
    shortType1.setCheck((short) 123);

    ShortType shortType2 = new ShortType();
    shortType2.setCheck((short) 45);

    assertNotEquals(shortType1, shortType2);

    assertMarshallingAndUnmarshalling(shortType1);
    assertMarshallingAndUnmarshalling(shortType2);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck(Short.MAX_VALUE);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck(Short.MIN_VALUE);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck((short) 0);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @Test
  public void testMarshallingWithNullValue() throws IOException {
    ShortType shortType = new ShortType();
    shortType.setCheck((short) 0);

    assertMarshallingAndUnmarshalling(shortType);
  }

  @YAMLMapper
  public static class ShortType {

    private short check;

    @Override
    public int hashCode() {
      return Objects.hash(getCheck());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ShortType)) {
        return false;
      }
      ShortType byteType = (ShortType) o;
      return getCheck() == byteType.getCheck();
    }

    public short getCheck() {
      return check;
    }

    public void setCheck(short check) {
      this.check = check;
    }
  }
}
