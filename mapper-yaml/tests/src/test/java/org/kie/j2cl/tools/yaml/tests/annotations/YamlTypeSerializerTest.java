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

package org.kie.j2cl.tools.yaml.tests.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;
import org.kie.j2cl.tools.yaml.tests.annotations.YamlTypeSerializerTest.TestHolder;

@J2clTestInput(YamlTypeSerializerTest.class)
public class YamlTypeSerializerTest extends AbstractYamlTest<TestHolder> {

  private static final YamlTypeSerializerTest_TestHolder_YamlMapperImpl mapper =
      YamlTypeSerializerTest_TestHolder_YamlMapperImpl.INSTANCE;

  public YamlTypeSerializerTest() {
    super(mapper);
  }

  @Test
  public void test() throws IOException {

    TestHolder testHolder = new TestHolder();
    Holder holder = new Holder();
    holder.value = "test";
    testHolder.setValue(holder);
    assertEquals("value: TEST", mapper.write(testHolder));
    assertTrue(mapper.read(mapper.write(testHolder)).getValue() instanceof Holder);

    Object[] values = new Object[] {new Holder("aaa"), new Holder("bbb"), new Holder("ccc")};
    testHolder.setValues(values);
    assertEquals(holder, mapper.read(mapper.write(testHolder)).getValue());
    assertEquals(mapper.write(testHolder), mapper.write(mapper.read(mapper.write(testHolder))));

    List list = new java.util.ArrayList();
    list.add(new Holder("aaa"));
    list.add(new Holder("bbb"));
    list.add(new Holder("ccc"));

    testHolder.setLists(list);

    assertEquals(mapper.write(testHolder), mapper.write(mapper.read(mapper.write(testHolder))));
    assertEquals(list, mapper.read(mapper.write(mapper.read(mapper.write(testHolder)))).lists);
  }

  @YAMLMapper
  public static class TestHolder {

    @YamlTypeSerializer(HolderSerializer.class)
    @YamlTypeDeserializer(HolderSerializer.class)
    private Object value;

    @YamlTypeSerializer(HolderSerializer.class)
    @YamlTypeDeserializer(HolderSerializer.class)
    private Object[] values;

    @YamlTypeSerializer(HolderSerializer.class)
    @YamlTypeDeserializer(HolderSerializer.class)
    private List lists;

    public Object getValue() {
      return value;
    }

    public void setValue(Object value) {
      this.value = value;
    }

    public Object[] getValues() {
      return values;
    }

    public void setValues(Object[] values) {
      this.values = values;
    }

    public List getLists() {
      return lists;
    }

    public void setLists(List lists) {
      this.lists = lists;
    }
  }

  public static class Holder {

    public Holder() {}

    public Holder(String value) {
      this.value = value;
    }

    private String value;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Holder holder = (Holder) o;
      return Objects.equals(value, holder.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }

  public static class HolderSerializer implements YAMLSerializer, YAMLDeserializer {

    @Override
    public void serialize(
        YamlMapping writer, String propertyName, Object value, YAMLSerializationContext ctx) {
      Holder holder = (Holder) value;
      writer.addScalarNode(propertyName, holder.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public void serialize(YamlSequence writer, Object value, YAMLSerializationContext ctx) {
      Holder holder = (Holder) value;
      writer.addScalarNode(holder.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public Object deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
        throws YAMLDeserializationException {
      return deserialize(yaml.getNode(key), ctx);
    }

    @Override
    public Object deserialize(YamlNode value, YAMLDeserializationContext ctx) {
      if (value == null || value.isEmpty()) {
        return null;
      }
      Holder holder = new Holder();
      holder.value = value.<String>asScalar().value().toLowerCase(Locale.ROOT);
      return holder;
    }
  }
}
