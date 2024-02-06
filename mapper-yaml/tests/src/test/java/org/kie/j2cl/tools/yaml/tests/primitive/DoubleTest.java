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
import org.kie.j2cl.tools.yaml.tests.primitive.DoubleTest.DoubleType;

@J2clTestInput(DoubleTest.class)
public class DoubleTest extends AbstractYamlTest<DoubleType> {

  private static final DoubleTest_DoubleType_YamlMapperImpl mapper =
      DoubleTest_DoubleType_YamlMapperImpl.INSTANCE;

  public DoubleTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(42.5);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(-10.75);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(0.0);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(Double.MAX_VALUE);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(Double.MIN_VALUE);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    DoubleType doubleType1 = new DoubleType();
    doubleType1.setValue(123.45);

    DoubleType doubleType2 = new DoubleType();
    doubleType2.setValue(123.45);

    assertEquals(doubleType1, doubleType2);
    assertEquals(doubleType1.hashCode(), doubleType2.hashCode());

    assertMarshallingAndUnmarshalling(doubleType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    DoubleType doubleType1 = new DoubleType();
    doubleType1.setValue(123.45);

    DoubleType doubleType2 = new DoubleType();
    doubleType2.setValue(67.89);

    assertNotEquals(doubleType1, doubleType2);

    assertMarshallingAndUnmarshalling(doubleType1);
    assertMarshallingAndUnmarshalling(doubleType2);
  }

  @Test
  public void testMarshallingWithInfinityValue() throws IOException {
    DoubleType doubleType = new DoubleType();
    doubleType.setValue(Double.POSITIVE_INFINITY);

    assertMarshallingAndUnmarshalling(doubleType);
  }

  @YAMLMapper
  public static class DoubleType {

    private double value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof DoubleType)) {
        return false;
      }
      DoubleType type = (DoubleType) o;
      return getValue() == type.getValue();
    }

    public double getValue() {
      return value;
    }

    public void setValue(double value) {
      this.value = value;
    }
  }
}
