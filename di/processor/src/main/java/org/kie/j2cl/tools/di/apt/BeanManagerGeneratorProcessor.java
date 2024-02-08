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

package org.kie.j2cl.tools.di.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.PrintWriterTreeLogger;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.task.BeanManagerGeneratorTask;
import org.kie.j2cl.tools.di.apt.task.CheckCyclesTask;
import org.kie.j2cl.tools.di.apt.task.FireAfterTask;
import org.kie.j2cl.tools.di.apt.task.RunContextTask;
import org.kie.j2cl.tools.di.apt.task.TaskGroup;
import org.kie.j2cl.tools.di.core.internal.step.BeanManagerStep;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BeanManagerGeneratorProcessor extends AbstractProcessor {

  private IOCContext iocContext;

  private static final TreeLogger logger = new PrintWriterTreeLogger();

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(BeanManagerStep.class.getCanonicalName());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    iocContext = ContextHolder.getInstance().getContext();

    if (annotations.isEmpty()) {
      return false;
    }
    if (iocContext == null) {
      return false;
    }

    TaskGroup taskGroup = new TaskGroup(logger.branch(TreeLogger.DEBUG, "start processing"));
    taskGroup.addTask(new CheckCyclesTask(iocContext, logger));
    taskGroup.addTask(new BeanManagerGeneratorTask(iocContext, logger));
    taskGroup.addTask(new FireAfterTask(iocContext, logger));
    taskGroup.addTask(new RunContextTask(iocContext, logger));
    taskGroup.execute();

    return false;
  }
}
