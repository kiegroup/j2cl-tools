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

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.primitive.LongTest.LongType;

@J2clTestInput(LongTest.class)
public class LongTest extends AbstractYamlTest<LongType> {

  private static final LongTest_LongType_YamlMapperImpl mapper =
      LongTest_LongType_YamlMapperImpl.INSTANCE;

  public LongTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingEmptyObject() throws IOException {
    LongType longType = new LongType();

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(42L);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(-10L);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(Long.MAX_VALUE);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(Long.MIN_VALUE);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(0L);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingLargeValue() throws IOException {
    LongType longType = new LongType();
    longType.setValue(987654321098765432L);

    assertMarshallingAndUnmarshalling(longType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    LongType longType1 = new LongType();
    longType1.setValue(123L);

    LongType longType2 = new LongType();
    longType2.setValue(123L);

    Assert.assertEquals(longType1, longType2);
    Assert.assertEquals(longType1.hashCode(), longType2.hashCode());

    assertMarshallingAndUnmarshalling(longType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    LongType longType1 = new LongType();
    longType1.setValue(123L);

    LongType longType2 = new LongType();
    longType2.setValue(456L);

    Assert.assertNotEquals(longType1, longType2);

    assertMarshallingAndUnmarshalling(longType1);
    assertMarshallingAndUnmarshalling(longType2);
  }

  @YAMLMapper
  public static class LongType {

    private long value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof LongType)) {
        return false;
      }
      LongType longType = (LongType) o;
      return getValue() == longType.getValue();
    }

    public long getValue() {
      return value;
    }

    public void setValue(long value) {
      this.value = value;
    }
  }
}
