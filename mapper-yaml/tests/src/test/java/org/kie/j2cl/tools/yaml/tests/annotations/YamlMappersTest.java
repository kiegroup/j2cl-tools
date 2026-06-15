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

import java.io.IOException;
import org.junit.Test;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlMappers;
import org.kie.j2cl.tools.yaml.tests.annotations.pojo.Project;
import org.kie.j2cl.tools.yaml.tests.annotations.pojo.Project_YamlMapperImpl;

@YamlMappers(Project.class)
public class YamlMappersTest {

  private final Project_YamlMapperImpl mapper = Project_YamlMapperImpl.INSTANCE;

  @Test
  public void yamlMappersTest() throws IOException {
    Project project = new Project();
    project.setName("test");
    project.setVersion("1.0.0");
    project.setDescription("The Project");

    String yaml = mapper.write(project);
    Project tested = mapper.read(yaml);
    assertEquals(project, tested);
  }
}
