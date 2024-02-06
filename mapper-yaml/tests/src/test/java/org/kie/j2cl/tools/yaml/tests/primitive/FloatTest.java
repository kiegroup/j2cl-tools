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
import org.kie.j2cl.tools.yaml.tests.primitive.FloatTest.FloatType;

@J2clTestInput(FloatTest.class)
public class FloatTest extends AbstractYamlTest<FloatType> {

  private static final FloatTest_FloatType_YamlMapperImpl mapper =
      FloatTest_FloatType_YamlMapperImpl.INSTANCE;

  public FloatTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(42.5f);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(-10.75f);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(0.0f);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(Float.MAX_VALUE);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(Float.MIN_VALUE);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    FloatType floatType1 = new FloatType();
    floatType1.setValue(123.45f);

    FloatType floatType2 = new FloatType();
    floatType2.setValue(123.45f);

    assertEquals(floatType1, floatType2);
    assertEquals(floatType1.hashCode(), floatType2.hashCode());

    assertMarshallingAndUnmarshalling(floatType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    FloatType floatType1 = new FloatType();
    floatType1.setValue(123.45f);

    FloatType floatType2 = new FloatType();
    floatType2.setValue(67.89f);

    assertNotEquals(floatType1, floatType2);

    assertMarshallingAndUnmarshalling(floatType1);
    assertMarshallingAndUnmarshalling(floatType2);
  }

  @Test
  public void testMarshallingWithInfinityValue() throws IOException {
    FloatType floatType = new FloatType();
    floatType.setValue(Float.POSITIVE_INFINITY);

    assertMarshallingAndUnmarshalling(floatType);
  }

  @YAMLMapper
  public static class FloatType {

    private float value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof FloatType)) {
        return false;
      }
      FloatType type = (FloatType) o;
      return getValue() == type.getValue();
    }

    public float getValue() {
      return value;
    }

    public void setValue(float value) {
      this.value = value;
    }
  }
}
