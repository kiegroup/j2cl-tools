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

package org.kie.j2cl.tools.yaml.tests.annotations.customtypeser;

import static org.junit.Assert.*;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.StringYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.StringYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.array.ArrayYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;
import org.kie.j2cl.tools.yaml.tests.AbstractYamlTest;

@J2clTestInput(YamlTypeSerializerHierarchyTest.class)
public class YamlTypeSerializerHierarchyTest
    extends AbstractYamlTest<YamlTypeSerializerHierarchyTest.Tested> {

  private static final YamlTypeSerializerHierarchyTest_Tested_YamlMapperImpl mapper =
      YamlTypeSerializerHierarchyTest_Tested_YamlMapperImpl.INSTANCE;

  public YamlTypeSerializerHierarchyTest() {
    super(mapper);
  }

  @Test
  public void testSerialize() throws IOException {
    Tested tested = new Tested();
    String result = mapper.write(tested);
    assertEquals("", result);
    assertNull(mapper.read(""));
  }

  @Test
  public void testSerializeString() throws IOException {
    Tested tested = new Tested();
    Nested nested = new Nested();
    nested.setValue("zzz");
    tested.setNested(nested);
    String result = mapper.write(tested);
    String expected = "nested:" + "\n" + "  value: zzz";
    assertEquals(expected, result);
    assertEquals(tested, mapper.read(expected));
  }

  @Test
  public void testSerializeStringArray() throws IOException {
    Tested tested = new Tested();
    Nested nested = new Nested();
    String[] array = new String[] {"zzz", "aaa", "bbb", "ccc"};
    nested.setValue(array);
    tested.setNested(nested);
    String result = mapper.write(tested);
    String expected =
        "nested:"
            + "\n"
            + "  value:"
            + "\n"
            + "    - zzz"
            + "\n"
            + "    - aaa"
            + "\n"
            + "    - bbb"
            + "\n"
            + "    - ccc";

    assertEquals(expected, result);
  }

  @Test
  public void testSerializeUser() throws IOException {
    Tested tested = new Tested();
    Nested nested = new Nested();
    User user = new User("user1", "email");

    nested.setValue(user);
    tested.setNested(nested);
    String result = mapper.write(tested);
    String expected =
        "nested:" + "\n" + "  value:" + "\n" + "    name: user1" + "\n" + "    email: email";
    assertEquals(expected, result);
    assertEquals(user, (User) mapper.read(expected).nested.value);
  }

  @Test
  public void testSerializeUsersArray() throws IOException {
    Tested tested = new Tested();
    Nested nested = new Nested();

    User[] users = new User[] {new User("user1", "email"), new User("user2", "email2")};

    nested.setValue(users);
    tested.setNested(nested);
    String result = mapper.write(tested);
    String expected =
        "nested:"
            + "\n"
            + "  value:"
            + "\n"
            + "    - name: user1"
            + "\n"
            + "      email: email"
            + "\n"
            + "    - name: user2"
            + "\n"
            + "      email: email2";
    assertEquals(expected, result);
  }

  @YAMLMapper
  public static class Tested {

    private Nested nested;

    public Nested getNested() {
      return nested;
    }

    public void setNested(Nested nested) {
      this.nested = nested;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Tested tested = (Tested) o;
      return Objects.equals(nested, tested.nested);
    }

    @Override
    public int hashCode() {
      return Objects.hash(nested);
    }
  }

  public static class Nested {

    @YamlTypeSerializer(HolderSerializer.class)
    @YamlTypeDeserializer(HolderSerializer.class)
    private Object value;

    public Object getValue() {
      return value;
    }

    public void setValue(Object value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Nested nested = (Nested) o;
      if (value instanceof String[] && nested.value instanceof String[]) {
        return Arrays.equals((String[]) value, (String[]) nested.value);
      }
      if (value instanceof User[] && nested.value instanceof User[]) {
        return Arrays.equals((User[]) value, (User[]) nested.value);
      }
      if (value instanceof User && nested.value instanceof User) {
        return value.equals(nested.value);
      }
      if (value instanceof String && nested.value instanceof String) {
        return value.equals(nested.value);
      }

      return Objects.equals(value, nested.value);
    }

    @Override
    public int hashCode() {
      if (value instanceof String[]) {
        return Arrays.hashCode((String[]) value);
      }
      if (value instanceof User[]) {
        return Arrays.hashCode((User[]) value);
      }
      if (value instanceof User) {
        return value.hashCode();
      }
      if (value instanceof String) {
        return value.hashCode();
      }

      return Objects.hash(value);
    }
  }

  @YAMLMapper
  public static class User {

    private String name;

    private String email;

    public User() {}

    public User(String name, String email) {
      this.name = name;
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, email);
    }
  }

  public static class HolderSerializer implements YAMLSerializer, YAMLDeserializer {

    private static final StringYAMLSerializer stringYAMLSerializer = new StringYAMLSerializer();
    private static final StringYAMLDeserializer stringYAMLDeserializer =
        new StringYAMLDeserializer();

    private static final YamlTypeSerializerHierarchyTest_User_YamlMapperImpl mapper =
        YamlTypeSerializerHierarchyTest_User_YamlMapperImpl.INSTANCE;

    @Override
    public Object deserialize(YamlMapping yaml, String key, YAMLDeserializationContext ctx)
        throws YAMLDeserializationException {
      if (yaml != null && yaml.getNode(key) != null) {
        return deserialize(yaml.getNode(key), ctx);
      }
      return null;
    }

    @Override
    public Object deserialize(YamlNode node, YAMLDeserializationContext ctx) {
      if (node != null) {
        if (node.type() == NodeType.SCALAR) {
          return stringYAMLDeserializer.deserialize(node, ctx);
        } else if (node.type() == NodeType.SEQUENCE) {
          List<String> result1 = new ArrayList<>();
          List<User> result2 = new ArrayList<>();
          YamlSequence sequence = node.asSequence();
          if (sequence != null) {
            sequence.values().stream()
                .forEach(
                    n -> {
                      if (n.type() == NodeType.SCALAR) {
                        result1.add(stringYAMLDeserializer.deserialize(n, ctx));
                      } else {
                        result2.add(mapper.getDeserializer().deserialize(n, ctx));
                      }
                    });
          }
          if (!result1.isEmpty()) {
            return result1.toArray(new String[result1.size()]);
          }
          return result2.toArray(new User[result2.size()]);
        } else if (node.type() == NodeType.MAPPING) {
          return mapper.getDeserializer().deserialize(node, ctx);
        }
      }
      return null;
    }

    @Override
    public void serialize(
        YamlMapping writer, String propertyName, Object value, YAMLSerializationContext ctx) {
      if (value instanceof String) {
        stringYAMLSerializer.serialize(writer, propertyName, (String) value, ctx);
      } else if (value instanceof User) {
        mapper.getSerializer().serialize(writer, propertyName, (User) value, ctx);
      } else if (value instanceof String[]) {
        new ArrayYAMLSerializer<>(stringYAMLSerializer)
            .serialize(writer, propertyName, (String[]) value, ctx);
      } else if (value instanceof User[]) {
        new ArrayYAMLSerializer<>(mapper.getSerializer())
            .serialize(writer, propertyName, (User[]) value, ctx);
      }
    }

    @Override
    public void serialize(YamlSequence writer, Object value, YAMLSerializationContext ctx) {
      if (value instanceof String) {
        stringYAMLSerializer.serialize(writer, (String) value, ctx);
      } else if (value instanceof User) {
        mapper.getSerializer().serialize(writer, (User) value, ctx);
      } else if (value instanceof String[]) {
        new ArrayYAMLSerializer<>(stringYAMLSerializer).serialize(writer, (String[]) value, ctx);
      } else if (value instanceof User[]) {
        new ArrayYAMLSerializer<>(mapper.getSerializer()).serialize(writer, (User[]) value, ctx);
      }
    }
  }
}
