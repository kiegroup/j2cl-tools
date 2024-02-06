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

package org.kie.j2cl.tools.yaml.tests;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.kie.j2cl.tools.yaml.mapper.api.AbstractObjectMapper;
import org.kie.j2cl.tools.yaml.mapper.api.DefaultYAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.utils.Pair;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.impl.Yaml;

public abstract class AbstractYamlTest<T> {

  protected final AbstractObjectMapper<T> mapper;

  protected static final YAMLSerializationContext doNotWriteEmptyArrays =
      DefaultYAMLSerializationContext.builder().writeEmptyYAMLArrays(false).build();

  protected AbstractYamlTest(AbstractObjectMapper<T> mapper) {
    this.mapper = mapper;
  }

  protected void assertMarshallingAndUnmarshalling(T value) throws IOException {
    String yaml = mapper.write(value);
    assertEquals(value, mapper.read(yaml));
  }

  protected void assertYamlsAreEqual(String yaml1, String yaml2) {
    assertTrue(checkYamlAreEqualInternal(yaml1, yaml2));
  }

  private boolean checkYamlAreEqualInternal(String yaml1, String yaml2) {
    final YamlMapping mapping1 = Yaml.fromString(yaml1);
    final YamlMapping mapping2 = Yaml.fromString(yaml2);
    Pair<YamlNode, YamlNode> pair = new Pair<>(mapping1, mapping2);
    Queue<Pair<YamlNode, YamlNode>> queue = new LinkedList<>();
    queue.add(pair);

    while (!queue.isEmpty()) {
      Pair<YamlNode, YamlNode> current = queue.poll();
      if (current.key.type() != current.value.type()) {
        return false;
      }
      if (current.key.type() == NodeType.MAPPING) {
        if (current.key.asMapping().keys().size() != current.value.asMapping().keys().size()) {
          return false;
        }

        for (String key : current.key.asMapping().keys()) {
          if (current.value.asMapping().getNode(key) == null) {
            return false;
          }
          queue.add(
              new Pair<>(
                  current.key.asMapping().getNode(key), current.value.asMapping().getNode(key)));
        }
      } else if (current.key.type() == NodeType.SEQUENCE) {
        if (current.key.asSequence().size() != current.value.asSequence().size()) {
          return false;
        }

        for (int i = 0; i < current.key.asSequence().size(); i++) {
          queue.add(
              new Pair<>(current.key.asSequence().node(i), current.value.asSequence().node(i)));
        }
      } else if (current.key.type() == NodeType.SCALAR) {
        if (!current.key.asScalar().value().equals(current.value.asScalar().value())) {
          return false;
        }
      }
    }
    return true;
  }

  public void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
    if (expecteds.length != actuals.length) {
      throw new AssertionError("Arrays have different lengths");
    }

    for (int i = 0; i < expecteds.length; i++) {
      if (expecteds[i] != actuals[i]) {
        throw new AssertionError("Arrays differ at index " + i);
      }
    }
  }

  public void assertArrayEquals(String[] expecteds, String[] actuals) {
    if (expecteds.length != actuals.length) {
      throw new AssertionError("Arrays have different lengths");
    }

    for (int i = 0; i < expecteds.length; i++) {
      if (!expecteds[i].equals(actuals[i])) {
        throw new AssertionError("Arrays differ at index " + i);
      }
    }
  }

  public void assertArrayEquals(char[] expecteds, char[] actuals) {
    if (expecteds.length != actuals.length) {
      throw new AssertionError("Arrays have different lengths");
    }

    for (int i = 0; i < expecteds.length; i++) {
      if (expecteds[i] != actuals[i]) {
        throw new AssertionError("Arrays differ at index " + i);
      }
    }
  }

  public void assertArrayEquals(byte[] expecteds, byte[] actuals) {
    if (expecteds.length != actuals.length) {
      throw new AssertionError("Arrays have different lengths");
    }

    for (int i = 0; i < expecteds.length; i++) {
      if (expecteds[i] != actuals[i]) {
        throw new AssertionError("Arrays differ at index " + i);
      }
    }
  }

  public void assertArrayEquals(Object[] expecteds, Object[] actuals) {
    if (expecteds.length != actuals.length) {
      throw new AssertionError("Arrays have different lengths");
    }

    for (int i = 0; i < expecteds.length; i++) {
      if (expecteds[i].equals(actuals[i])) {
        throw new AssertionError("Arrays differ at index " + i);
      }
    }
  }
}
