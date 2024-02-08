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

package org.kie.j2cl.tools.di.ui.elemental2;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.InjectableVariableDefinition;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;

import static org.kie.j2cl.tools.di.ui.elemental2.ElmToTagMapping.HTML_ELEMENTS;

@Generator(priority = 100000)
public class Elemenatal2FactoryGenerator extends IOCGenerator<BeanDefinition> {

    public Elemenatal2FactoryGenerator(TreeLogger treeLogger, IOCContext iocContext) {
        super(treeLogger, iocContext);
    }

    public static Set<Map.Entry<Class<? extends HTMLElement>, String>> getHTMLElementByTag(
            String tag) {
        return HTML_ELEMENTS.entries().stream().filter(elm -> elm.getValue().equals(tag))
                .collect(Collectors.toSet());
    }

    @Override
    public void register() {
        HTML_ELEMENTS.keySet().forEach(clazz -> {
            iocContext.register(Inject.class, clazz, WiringElementType.FIELD_TYPE, this);
            iocContext.getBuildIn().add(clazz.getCanonicalName());
        });
    }

    @Override
    public String generateBeanLookupCall(InjectableVariableDefinition fieldPoint) {
        return generationUtils.wrapCallInstanceImpl(new MethodCallExpr(
                new FieldAccessExpr(new NameExpr(DomGlobal.class.getCanonicalName()), "document"),
                "createElement").addArgument(getTagFromType(fieldPoint))).toString();
    }

    private StringLiteralExpr getTagFromType(InjectableVariableDefinition fieldPoint) {
        if (fieldPoint.getVariableElement().getAnnotation(Named.class) != null
                && !fieldPoint.getVariableElement().getAnnotation(Named.class).value().equals("")) {
            return new StringLiteralExpr(fieldPoint.getVariableElement().getAnnotation(Named.class)
                    .value().toLowerCase(Locale.ROOT));
        }

        Class<? extends HTMLElement> clazz;
        try {
            clazz = (Class<? extends HTMLElement>) Class.forName(MoreTypes
                    .asTypeElement(fieldPoint.getVariableElement().asType()).getQualifiedName().toString());
        } catch (ClassNotFoundException e) {
            throw new Error(
                    "Unable to process " + MoreTypes.asTypeElement(fieldPoint.getVariableElement().asType())
                            .getQualifiedName().toString() + " " + e.getMessage());
        }

        if (!HTML_ELEMENTS.containsKey(clazz)) {
            throw new GenerationException("Unable to process " + MoreTypes
                    .asTypeElement(fieldPoint.getVariableElement().asType()).getQualifiedName().toString());
        }

        long count = HTML_ELEMENTS.get(clazz).size();

        if (count > 1) {
            throw new GenerationException("Unable to process "
                    + MoreTypes.asTypeElement(fieldPoint.getVariableElement().asType()).getQualifiedName()
                    .toString()
                    + ", " + MoreElements.asType(fieldPoint.getVariableElement().getEnclosingElement()) + "."
                    + fieldPoint.getVariableElement().getSimpleName()
                    + " must be annotated with @Named(\"tag_name\")");
        }

        return new StringLiteralExpr(HTML_ELEMENTS.get(clazz).stream().findFirst().get());
    }

}
