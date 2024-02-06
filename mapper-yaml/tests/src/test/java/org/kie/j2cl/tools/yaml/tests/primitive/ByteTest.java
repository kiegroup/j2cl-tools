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
import org.kie.j2cl.tools.yaml.tests.primitive.ByteTest.ByteType;

@J2clTestInput(ByteTest.class)
public class ByteTest extends AbstractYamlTest<ByteType> {

  private static final String YAML_0 = "check: 0";
  private static final String YAML_123 = "check: 123";
  private static final String YAML_22 = "check: -22";

  private static ByteTest_ByteType_YamlMapperImpl mapper =
      ByteTest_ByteType_YamlMapperImpl.INSTANCE;

  public ByteTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck((byte) 42);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck((byte) -10);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    ByteType byteType1 = new ByteType();
    byteType1.setCheck((byte) 123);

    ByteType byteType2 = new ByteType();
    byteType2.setCheck((byte) 123);

    assertEquals(byteType1, byteType2);
    assertEquals(byteType1.hashCode(), byteType2.hashCode());

    assertMarshallingAndUnmarshalling(byteType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    ByteType byteType1 = new ByteType();
    byteType1.setCheck((byte) 123);

    ByteType byteType2 = new ByteType();
    byteType2.setCheck((byte) 45);

    assertNotEquals(byteType1, byteType2);

    assertMarshallingAndUnmarshalling(byteType1);
    assertMarshallingAndUnmarshalling(byteType2);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck(Byte.MAX_VALUE);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck(Byte.MIN_VALUE);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck((byte) 0);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @Test
  public void testMarshallingWithNullValue() throws IOException {
    ByteType byteType = new ByteType();
    byteType.setCheck((byte) 0);

    assertMarshallingAndUnmarshalling(byteType);
  }

  @YAMLMapper
  public static class ByteType {

    private byte check;

    @Override
    public int hashCode() {
      return Objects.hash(getCheck());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ByteType)) {
        return false;
      }
      ByteType byteType = (ByteType) o;
      return getCheck() == byteType.getCheck();
    }

    public byte getCheck() {
      return check;
    }

    public void setCheck(byte check) {
      this.check = check;
    }
  }
}
