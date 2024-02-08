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

package org.kie.j2cl.tools.di.ui.translation.generator;

import jakarta.inject.Inject;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.InjectableVariableDefinition;
import org.kie.j2cl.tools.di.apt.generator.api.ClassMetaInfo;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.core.InstanceFactory;
import org.kie.j2cl.tools.di.ui.translation.client.TranslationService;

@Generator(priority = 100002)
public class TranslationServiceGenerator extends IOCGenerator<BeanDefinition> {

    public TranslationServiceGenerator(TreeLogger treeLogger, IOCContext iocContext) {
        super(treeLogger, iocContext);
    }

    @Override
    public void register() {
        iocContext.register(Inject.class, TranslationService.class, WiringElementType.BEAN, this);
    }

    @Override
    public void generate(ClassMetaInfo classMetaInfo, BeanDefinition beanDefinition) {
        new TranslationServiceImplGenerator(iocContext).generate();
    }

    @Override
    public String generateBeanLookupCall(InjectableVariableDefinition fieldPoint) {
        ClassOrInterfaceType type = new ClassOrInterfaceType();
        type.setName(InstanceFactory.class.getCanonicalName());
        type.setTypeArguments(
                new ClassOrInterfaceType().setName(TranslationService.class.getCanonicalName()));

        NodeList<BodyDeclaration<?>> supplierClassBody = new NodeList<>();

        MethodDeclaration getInstance = new MethodDeclaration();
        getInstance.setModifiers(com.github.javaparser.ast.Modifier.Keyword.PUBLIC);
        getInstance.setName("getInstance");
        getInstance.addAnnotation(Override.class);
        getInstance
                .setType(new ClassOrInterfaceType().setName(TranslationService.class.getCanonicalName()));

        getInstance.getBody().get().addAndGetStatement(new ReturnStmt(new FieldAccessExpr(
                new NameExpr(TranslationService.class.getCanonicalName() + "Impl"), "INSTANCE")));
        supplierClassBody.add(getInstance);

        return new ObjectCreationExpr().setType(type).setAnonymousClassBody(supplierClassBody)
                .toString();
    }

}
