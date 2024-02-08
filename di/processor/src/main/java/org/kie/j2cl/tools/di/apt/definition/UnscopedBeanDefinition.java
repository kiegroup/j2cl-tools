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

package org.kie.j2cl.tools.di.apt.definition;

import javax.lang.model.type.TypeMirror;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.google.auto.common.MoreTypes;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.apt.util.TypeUtils;
import org.kie.j2cl.tools.di.core.internal.SimpleInstanceFactoryImpl;

public class UnscopedBeanDefinition extends BeanDefinition {

  public UnscopedBeanDefinition(TypeMirror type, TreeLogger logger, IOCContext context) {
    super(type);
    setIocGenerator(new UnscopedIOCGenerator(logger, context));
  }

  private static class UnscopedIOCGenerator extends IOCGenerator<BeanDefinition> {

    private UnscopedIOCGenerator(TreeLogger logger, IOCContext context) {
      super(logger, context);
    }

    @Override
    public void register() {

    }

    public String generateBeanLookupCall(InjectableVariableDefinition fieldPoint) {

      String clazzName = TypeUtils
          .getQualifiedName(MoreTypes.asTypeElement(fieldPoint.getVariableElement().asType()));
      return new ObjectCreationExpr().setType(SimpleInstanceFactoryImpl.class.getCanonicalName())
          .addArgument(new ObjectCreationExpr().setType(clazzName)).toString();

    }
  }

}
