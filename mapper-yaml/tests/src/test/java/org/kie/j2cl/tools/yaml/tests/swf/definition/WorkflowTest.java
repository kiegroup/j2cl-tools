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

package org.kie.j2cl.tools.yaml.tests.swf.definition;

import static junit.framework.TestCase.assertEquals;

import java.util.LinkedList;
import java.util.Queue;
import org.kie.j2cl.tools.yaml.mapper.api.internal.utils.Pair;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.impl.Yaml;
import org.snakeyaml.engine.v2.GwtIncompatible;

@GwtIncompatible
public class WorkflowTest {

  private static final Workflow_YamlMapperImpl mapper = Workflow_YamlMapperImpl.INSTANCE;

  // @Test
  public void testEmpty() {
    Workflow workflow = new Workflow();
    assertEquals("", mapper.write(workflow));
  }

  /*  @GwtIncompatible
  // @Test
  public void testSolveMathProblemsExample() throws Exception {
    String yaml = readTestResource("SolveMathProblemsExample.sw.yaml");
    Workflow workflow = mapper.read(yaml);

    System.out.println("1: \n" + yaml);
    System.out.println("2: \n" + mapper.write(workflow));

    assertTrue(checkYamlAreEqual(yaml, mapper.write(workflow)));
  }

  //@Test
  @GwtIncompatible
  public void FinalizeCollegeApplicationExampleTest() throws IOException {
    String yaml = readTestResource("FinalizeCollegeApplicationExample.sw.yaml");
    Workflow workflow = mapper.read(yaml);

    Object endObject = workflow.getStates()[0].getEnd();
    assertNotNull(endObject);
    StateEnd stateEnd = (StateEnd) endObject;
    assertTrue(stateEnd.terminate);

    assertTrue(checkYamlAreEqual(yaml, mapper.write(workflow)));
  }

  private String readTestResource(final String fileName) throws IOException {
    return new String(
            org.apache.commons.io.IOUtils.toByteArray(
                    java.nio.file.Files.newInputStream(new File("src/test/resources/" + fileName).toPath())));
  }*/

  private boolean checkYamlAreEqual(String yaml1, String yaml2) {
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
}
