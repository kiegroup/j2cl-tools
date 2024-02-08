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

package org.kie.j2cl.tools.di.apt.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.ejb.Startup;

import com.github.javaparser.ast.expr.Expression;
import org.kie.j2cl.tools.di.annotation.Application;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.generator.api.ClassMetaInfo;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.ExecutionEnv;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.generator.helpers.FreemarkerTemplateGenerator;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;

import static org.kie.j2cl.tools.di.apt.util.TypeUtils.isDependent;

@Generator(priority = 100000)
public class BootstrapperGenerator extends ManagedBeanGenerator {

  private final FreemarkerTemplateGenerator freemarkerTemplateGenerator =
      new FreemarkerTemplateGenerator("bootstrap.ftlh");

  public BootstrapperGenerator(TreeLogger treeLogger, IOCContext iocContext) {
    super(treeLogger, iocContext);
  }

  @Override
  public void register() {
    iocContext.register(Application.class, WiringElementType.BEAN, this);
  }

  @Override
  public void generate(ClassMetaInfo classMetaInfo, BeanDefinition beanDefinition) {
    Map<String, Object> root = new HashMap<>();
    List<Dep> fields = new ArrayList<>();

    root.put("jre", iocContext.getGenerationContext().getExecutionEnv().equals(ExecutionEnv.JRE));
    root.put("package", beanDefinition.getPackageName());
    root.put("bean", beanDefinition.getSimpleClassName());
    root.put("imports", classMetaInfo.getImports());
    root.put("deps", fields);


    deps(beanDefinition, fields);
    fieldDecorators(beanDefinition, classMetaInfo);
    initInterceptors(beanDefinition, root);
    methodDecorators(beanDefinition, classMetaInfo);
    postConstruct(beanDefinition, root);
    runOnStartup(root);


    root.put("fields", classMetaInfo.getBodyStatements());
    root.put("preDestroy", classMetaInfo.getOnDestroy());
    root.put("doInitInstance", classMetaInfo.getDoInitInstance());

    String source = freemarkerTemplateGenerator.toSource(root);
    String BOOTSTRAP_EXTENSION = "Bootstrap";
    String fileName = beanDefinition.getPackageName() + "." + beanDefinition.getSimpleClassName()
        + BOOTSTRAP_EXTENSION;
    writeJavaFile(fileName, source);
  }

  private void initInterceptors(BeanDefinition beanDefinition, Map<String, Object> root) {
    if (iocContext.getGenerationContext().getExecutionEnv().equals(ExecutionEnv.J2CL)) {
      if (iocContext.getGenerationContext().getExecutionEnv().equals(ExecutionEnv.J2CL)) {
        List<String> fieldInterceptors = new ArrayList<>();
        root.put("fieldInterceptors", fieldInterceptors);
        beanDefinition.getFields().forEach(fieldPoint -> {
          Expression expr = generationUtils.getFieldAccessorExpression(fieldPoint, "field");
          fieldInterceptors.add(expr.toString());
        });
      }
    }
  }

  private void runOnStartup(Map<String, Object> root) {
    Set<String> onStartup = iocContext.getTypeElementsByAnnotation(Startup.class.getCanonicalName())
        .stream().map(type -> {
          if (isDependent(iocContext.getBean(type.asType()))) {
            throw new GenerationException(
                "Bean, annotated with @Startup, must be @Singleton or @ApplicationScoped : "
                    + type);
          }
          return type.asType().toString();
        }).collect(Collectors.toSet());
    root.put("onStartup", onStartup);
  }

}
