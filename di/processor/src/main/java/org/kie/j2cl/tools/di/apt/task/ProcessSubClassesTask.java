/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.kie.j2cl.tools.di.apt.task;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;

public class ProcessSubClassesTask implements Task {

  private IOCContext context;
  private TreeLogger logger;

  public ProcessSubClassesTask(IOCContext context, TreeLogger logger) {
    this.context = context;
    this.logger = logger;
  }

  @Override
  public void execute() throws UnableToCompleteException {
    context.getBeans().values().forEach(bean -> {
      Set<BeanDefinition> flattened =
          bean.getSubclasses().stream().flatMap(Helper::flatten).collect(Collectors.toSet());
      bean.getSubclasses().addAll(flattened);
    });
  }

  private static class Helper {

    private Helper() {}

    public static Stream<BeanDefinition> flatten(BeanDefinition order) {
      return Stream.concat(Stream.of(order),
          order.getSubclasses().stream().flatMap(Helper::flatten));
    }
  }
}
