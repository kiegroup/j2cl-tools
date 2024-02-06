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
import org.kie.j2cl.tools.yaml.tests.primitive.IntTest.IntType;

@J2clTestInput(IntTest.class)
public class IntTest extends AbstractYamlTest<IntType> {

  private static final String YAML_0 = "value: 0";
  private static final String YAML_17222 = "value: 17222";
  private static final String YAML__17222 = "value: -17222";

  private static final IntTest_IntType_YamlMapperImpl mapper =
      IntTest_IntType_YamlMapperImpl.INSTANCE;

  public IntTest() {
    super(mapper);
  }

  @Test
  public void testMarshallingPositiveValue() throws IOException {
    IntType intType = new IntType();
    intType.setValue(42);

    assertMarshallingAndUnmarshalling(intType);
  }

  @Test
  public void testMarshallingNegativeValue() throws IOException {
    IntType intType = new IntType();
    intType.setValue(-10);

    assertMarshallingAndUnmarshalling(intType);
  }

  @Test
  public void testMarshallingZeroValue() throws IOException {
    IntType intType = new IntType();
    intType.setValue(0);

    assertMarshallingAndUnmarshalling(intType);
  }

  @Test
  public void testMarshallingMaxValue() throws IOException {
    IntType intType = new IntType();
    intType.setValue(Integer.MAX_VALUE);

    assertMarshallingAndUnmarshalling(intType);
  }

  @Test
  public void testMarshallingMinValue() throws IOException {
    IntType intType = new IntType();
    intType.setValue(Integer.MIN_VALUE);

    assertMarshallingAndUnmarshalling(intType);
  }

  @Test
  public void testMarshallingEqualsAndHashCode() throws IOException {
    IntType intType1 = new IntType();
    intType1.setValue(123);

    IntType intType2 = new IntType();
    intType2.setValue(123);

    assertEquals(intType1, intType2);
    assertEquals(intType1.hashCode(), intType2.hashCode());

    assertMarshallingAndUnmarshalling(intType1);
  }

  @Test
  public void testMarshallingNotEquals() throws IOException {
    IntType intType1 = new IntType();
    intType1.setValue(123);

    IntType intType2 = new IntType();
    intType2.setValue(456);

    assertNotEquals(intType1, intType2);

    assertMarshallingAndUnmarshalling(intType1);
    assertMarshallingAndUnmarshalling(intType2);
  }

  @YAMLMapper
  public static class IntType {

    private int value;

    @Override
    public int hashCode() {
      return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof IntType)) {
        return false;
      }
      IntType intType = (IntType) o;
      return getValue() == intType.getValue();
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }
}
