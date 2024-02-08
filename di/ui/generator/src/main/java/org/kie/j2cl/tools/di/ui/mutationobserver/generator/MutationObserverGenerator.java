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

package org.kie.j2cl.tools.di.ui.mutationobserver.generator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.google.auto.common.MoreElements;
import elemental2.dom.HTMLElement;
import elemental2.dom.MutationRecord;
import org.kie.j2cl.tools.di.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.di.apt.definition.MethodDefinition;
import org.kie.j2cl.tools.di.apt.exception.GenerationException;
import org.kie.j2cl.tools.di.apt.generator.api.ClassMetaInfo;
import org.kie.j2cl.tools.di.apt.generator.api.Generator;
import org.kie.j2cl.tools.di.apt.generator.api.IOCGenerator;
import org.kie.j2cl.tools.di.apt.generator.api.WiringElementType;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.logger.TreeLogger;
import org.kie.j2cl.tools.di.ui.mutationobserver.client.MutationObserver;
import org.kie.j2cl.tools.di.ui.mutationobserver.client.ObserverCallback;
import org.kie.j2cl.tools.di.ui.mutationobserver.client.OnAttach;
import org.kie.j2cl.tools.di.ui.mutationobserver.client.OnDetach;



@Generator(priority = 100002)
public class MutationObserverGenerator extends IOCGenerator<MethodDefinition> {

    private TypeMirror htmlElement;

    private BeanDefinition mutationObserverBeanDefinition;

    public MutationObserverGenerator(TreeLogger logger, IOCContext iocContext) {
        super(logger, iocContext);
    }

    @Override
    public void register() {
        iocContext.register(OnAttach.class, WiringElementType.METHOD_DECORATOR, this);
        iocContext.register(OnDetach.class, WiringElementType.METHOD_DECORATOR, this);

        htmlElement = iocContext.getGenerationContext().getElements()
                .getTypeElement(HTMLElement.class.getCanonicalName()).asType();
    }

    public void generate(ClassMetaInfo builder, MethodDefinition mutationObserver) {
        ifValid(mutationObserver);
        VariableElement target = findField(mutationObserver);
        isValid(target);
        generateCallback(builder, mutationObserver);
    }

    private void ifValid(MethodDefinition mutationObserver) {
        if (mutationObserver.getExecutableElement().getParameters().size() > 1
                || mutationObserver.getExecutableElement().getParameters().isEmpty()) {
            throw new GenerationException(
                    "Method [" + mutationObserver.getExecutableElement().getSimpleName() + "] in "
                            + mutationObserver.getExecutableElement().getEnclosingElement()
                            + " must have only one arg of type MutationRecord");
        }

        if (mutationObserver.getExecutableElement().getModifiers().contains(Modifier.PRIVATE)) {
            throw new GenerationException("Method ["
                    + mutationObserver.getExecutableElement().getSimpleName() + "] in "
                    + mutationObserver.getExecutableElement().getEnclosingElement() + " must not be private");
        }

        if (mutationObserver.getExecutableElement().getModifiers().contains(Modifier.STATIC)) {
            throw new GenerationException("Method ["
                    + mutationObserver.getExecutableElement().getSimpleName() + "] in "
                    + mutationObserver.getExecutableElement().getEnclosingElement() + " must not be static");
        }

        TypeMirror mutationRecord = iocContext.getGenerationContext().getElements()
                .getTypeElement(MutationRecord.class.getCanonicalName()).asType();

        if (!mutationObserver.getExecutableElement().getParameters().get(0).asType()
                .equals(mutationRecord)) {
            throw new GenerationException(
                    "Method [" + mutationObserver.getExecutableElement().getSimpleName() + "] in "
                            + mutationObserver.getExecutableElement().getEnclosingElement()
                            + " must have arg of type MutationRecord");
        }
    }

    private VariableElement findField(MethodDefinition mutationObserver) {
        String fieldName = findFieldName(mutationObserver.getExecutableElement());

        Element target = mutationObserver.getExecutableElement().getEnclosingElement()
                .getEnclosedElements().stream().filter(elm -> elm.getKind().equals(ElementKind.FIELD))
                .filter(elm -> MoreElements.asVariable(elm).getSimpleName().toString().equals(fieldName))
                .findAny().orElseThrow(() -> new Error("Unable to find field named " + fieldName + " in "
                        + mutationObserver.getExecutableElement().getEnclosingElement()));

        return MoreElements.asVariable(target);
    }

    private void isValid(VariableElement target) {
        if (target.getModifiers().contains(Modifier.PRIVATE)) {
            throw new GenerationException("MutationObserver target  [" + target.getSimpleName() + " in "
                    + target.getEnclosingElement() + " must not be private");
        }

        if (target.getModifiers().contains(Modifier.STATIC)) {
            throw new GenerationException("MutationObserver target  [" + target.getSimpleName() + " in "
                    + target.getEnclosingElement() + " must not be static");
        }

        if (iocContext.getGenerationContext().getTypes().isSubtype(htmlElement, target.asType())) {
            throw new GenerationException("MutationObserver target  [" + target.getSimpleName() + " in "
                    + target.getEnclosingElement() + " must be subtype of HTMLElement atm");
        }
    }

    // TODO replace with template
    public void generateCallback(ClassMetaInfo builder, MethodDefinition definition) {
        builder.addImport(MutationObserver.class);
        builder.addImport(ObserverCallback.class);

        String callbackMethodName =
                definition.getExecutableElement().getAnnotation(OnAttach.class) != null
                        ? "addOnAttachListener"
                        : "addOnDetachListener";

        String fieldName = definition.getExecutableElement().getAnnotation(OnAttach.class) != null
                ? definition.getExecutableElement().getAnnotation(OnAttach.class).value()
                : definition.getExecutableElement().getAnnotation(OnDetach.class).value();

        EnclosedExpr castToAbstractEventHandler =
                new EnclosedExpr(new CastExpr(new ClassOrInterfaceType().setName("MutationObserver"),
                        new MethodCallExpr(new MethodCallExpr(new NameExpr("beanManager"), "lookupBean")
                                .addArgument("MutationObserver.class"), "getInstance")));

        builder.addToDoInitInstance(
                () -> new MethodCallExpr(castToAbstractEventHandler, callbackMethodName)
                        .addArgument("instance." + fieldName)
                        .addArgument("(ObserverCallback) m -> instance."
                                + definition.getExecutableElement().getSimpleName().toString() + "(m)")
                        .toString() + ";");
    }

    private String findFieldName(ExecutableElement executableElement) {
        OnDetach onDetach = executableElement.getAnnotation(OnDetach.class);
        if (onDetach != null && !onDetach.value().isEmpty()) {
            return onDetach.value();
        }

        OnAttach onAttach = executableElement.getAnnotation(OnAttach.class);
        if (onAttach != null && !onAttach.value().isEmpty()) {
            return onAttach.value();
        }
        throw new Error("Unable to find field name");
    }

}
