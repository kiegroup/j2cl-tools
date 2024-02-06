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

package org.kie.j2cl.tools.yaml.tests.beans;

import static org.junit.Assert.*;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(UnknownPropertyTest.class)
public class UnknownPropertyTest extends AbstractYamlTest<UnknownPropertyTest.Tested> {

  private static final UnknownPropertyTest_Tested_YamlMapperImpl mapper =
      UnknownPropertyTest_Tested_YamlMapperImpl.INSTANCE;

  public UnknownPropertyTest() {
    super(mapper);
  }

  @Test
  public void testUnknownProperty() throws IOException {
    Tested tested = new Tested();
    String yaml = mapper.write(tested);
    assertEquals("", yaml);
    assertNull(mapper.read(yaml));
  }

  @Test
  public void testUnknownProperty2() throws IOException {
    String expected =
        "foo: bar"
            + "\n"
            + "pleh: help"
            + "\n"
            + "stuff:"
            + "\n"
            + "  foo: bar"
            + "\n"
            + "  bar: foo";

    assertNull(mapper.read(expected));
  }

  @YAMLMapper
  public static class Tested {

    private String value;

    private String[] array;

    private List<String> list;

    private Set<String> set;

    private List<Inner> innerList;

    private Set<Inner> innerSet;

    private Type type;

    private Inner inner;

    public Inner getInner() {
      return inner;
    }

    public List<Inner> getInnerList() {
      return innerList;
    }

    public void setInnerList(List<Inner> innerList) {
      this.innerList = innerList;
    }

    public void setInner(Inner inner) {
      this.inner = inner;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Tested tested = (Tested) o;
      return Objects.equals(value, tested.value) && Objects.equals(inner, tested.inner);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, inner);
    }

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
    }

    public String[] getArray() {
      return array;
    }

    public void setArray(String[] array) {
      this.array = array;
    }

    public List<String> getList() {
      return list;
    }

    public void setList(List<String> list) {
      this.list = list;
    }

    public Set<String> getSet() {
      return set;
    }

    public void setSet(Set<String> set) {
      this.set = set;
    }

    public Set<Inner> getInnerSet() {
      return innerSet;
    }

    public void setInnerSet(Set<Inner> innerSet) {
      this.innerSet = innerSet;
    }
  }

  public enum Type {
    A,
    B,
    C
  }

  public static class Inner {

    private String input;
    private String output;

    private Type type;

    public Inner() {}

    public Inner(String input, String output) {
      this.input = input;
      this.output = output;
    }

    public final String getInput() {
      return input;
    }

    public final void setInput(String input) {
      this.input = input;
    }

    public final String getOutput() {
      return output;
    }

    public final void setOutput(String output) {
      this.output = output;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Inner inner = (Inner) o;
      return Objects.equals(input, inner.input) && Objects.equals(output, inner.output);
    }

    @Override
    public int hashCode() {
      return Objects.hash(input, output);
    }

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
    }
  }
}
