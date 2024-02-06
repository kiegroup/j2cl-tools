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

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.Locale;
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
import org.kie.j2cl.tools.yaml.tests.annotations.YamlTypeSerializerWithAnnotatedTypeTest.Tested;

@J2clTestInput(YamlTypeSerializerWithAnnotatedTypeTest.class)
public class YamlTypeSerializerWithAnnotatedTypeTest extends AbstractYamlTest<Tested> {

  private static final YamlTypeSerializerWithAnnotatedTypeTest_Tested_YamlMapperImpl mapper =
      YamlTypeSerializerWithAnnotatedTypeTest_Tested_YamlMapperImpl.INSTANCE;

  public YamlTypeSerializerWithAnnotatedTypeTest() {
    super(mapper);
  }

  @Test
  public void test() {
    Tested tested = new Tested();
    assertEquals("", mapper.write(tested));
  }

  @Test
  public void test2() throws IOException {
    ValueHolder valueHolder = new ValueHolder("zzz");
    Tested tested = new Tested();
    tested.setValue(valueHolder);
    assertEquals("value: ZZZ", mapper.write(tested));
    assertEquals("zzz", mapper.read(mapper.write(tested)).getValue().getValue());
  }

  @YAMLMapper
  public static class Tested {
    private ValueHolder value;

    public ValueHolder getValue() {
      return value;
    }

    public void setValue(ValueHolder value) {
      this.value = value;
    }
  }

  @YamlTypeSerializer(HolderSerializer.class)
  @YamlTypeDeserializer(HolderSerializer.class)
  public static class ValueHolder {
    private String value;

    public ValueHolder() {}

    public ValueHolder(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  public static class HolderSerializer
      implements YAMLSerializer<ValueHolder>, YAMLDeserializer<ValueHolder> {

    @Override
    public void serialize(
        YamlMapping writer, String propertyName, ValueHolder value, YAMLSerializationContext ctx) {
      writer.addScalarNode(propertyName, value.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public void serialize(YamlSequence writer, ValueHolder value, YAMLSerializationContext ctx) {
      writer.addScalarNode(value.value.toUpperCase(Locale.ROOT));
    }

    @Override
    public ValueHolder deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
        throws YAMLDeserializationException {
      return deserialize(yaml.getNode(key), ctx);
    }

    @Override
    public ValueHolder deserialize(YamlNode value, YAMLDeserializationContext ctx) {
      if (value == null || value.isEmpty()) {
        return null;
      }
      ValueHolder holder = new ValueHolder();
      holder.value = value.<String>asScalar().value().toLowerCase(Locale.ROOT);
      return holder;
    }
  }
}
