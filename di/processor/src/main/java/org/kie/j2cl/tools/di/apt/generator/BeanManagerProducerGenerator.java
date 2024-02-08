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

import jakarta.inject.Inject;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import org.kie.j2cl.tools.di.apt.definition.Definition;
import org.kie.j2cl.tools.di.apt.definition.InjectableVariableDefinition;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.core.BeanManager;

@Generator()
public class BeanManagerProducerGenerator extends IOCGenerator<Definition> {

    public BeanManagerProducerGenerator(TreeLogger treeLogger, IOCContext iocContext) {
        super(treeLogger, iocContext);
    }

    @Override
    public void register() {
        iocContext.register(Inject.class, BeanManager.class, WiringElementType.FIELD_TYPE, this);
    }

    public String generateBeanLookupCall(InjectableVariableDefinition fieldPoint) {
        String typeQualifiedName = generationUtils.getActualQualifiedBeanName(fieldPoint);
        return new MethodCallExpr(new NameExpr("beanManager"), "lookupBean")
                .addArgument(new FieldAccessExpr(new NameExpr(typeQualifiedName), "class")).toString();
    }
}
