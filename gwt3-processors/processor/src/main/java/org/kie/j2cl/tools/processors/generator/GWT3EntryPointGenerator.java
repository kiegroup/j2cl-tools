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

package org.kie.j2cl.tools.processors.generator;

import com.google.auto.common.MoreElements;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import org.kie.j2cl.tools.processors.annotations.GWT3EntryPoint;
import org.kie.j2cl.tools.processors.context.AptContext;
import org.kie.j2cl.tools.processors.exception.GenerationException;

public class GWT3EntryPointGenerator extends AbstractGenerator {

  public GWT3EntryPointGenerator(AptContext context) {
    super(context, GWT3EntryPoint.class);
  }

  @Override
  public void generate(Set<Element> elements) {
    elements.forEach(this::generate);
  }

  public void generate(Element element) {
    generate(checkMethod(element));
  }

  private ExecutableElement checkMethod(Element target) {
    if (!target.getKind().equals(ElementKind.METHOD)) {
      throw new GenerationException(
          "Only method can be annotated with " + GWT3EntryPoint.class.getCanonicalName());
    }
    ExecutableElement methodInfo = (ExecutableElement) target;
    if (!methodInfo.getParameters().isEmpty()) {
      throw new GenerationException(
          "Method, annotated "
              + GWT3EntryPoint.class.getCanonicalName()
              + " , must have no params");
    }

    if (methodInfo.getModifiers().contains(javax.lang.model.element.Modifier.STATIC)) {
      throw new GenerationException(
          "Method, annotated with "
              + GWT3EntryPoint.class.getCanonicalName()
              + ", must not be static");
    }

    if (!methodInfo.getModifiers().contains(javax.lang.model.element.Modifier.PUBLIC)) {
      throw new GenerationException(
          "Method, annotated with " + GWT3EntryPoint.class.getCanonicalName() + ", must be public");
    }

    if (methodInfo.getModifiers().contains(javax.lang.model.element.Modifier.ABSTRACT)) {
      throw new GenerationException(
          "Method, annotated with "
              + GWT3EntryPoint.class.getCanonicalName()
              + ", must not be abstract");
    }

    if (methodInfo.getModifiers().contains(javax.lang.model.element.Modifier.NATIVE)) {
      throw new GenerationException(
          "Method, annotated with "
              + GWT3EntryPoint.class.getCanonicalName()
              + ", must not be native");
    }
    Element clazz = methodInfo.getEnclosingElement();

    if (clazz.getModifiers().contains(javax.lang.model.element.Modifier.ABSTRACT)) {
      throw new GenerationException(
          "Class with method, annotated with "
              + GWT3EntryPoint.class.getCanonicalName()
              + ", must not be abstract");
    }

    if (!clazz.getModifiers().contains(Modifier.PUBLIC)) {
      throw new GenerationException(
          "Class with method, annotated with "
              + GWT3EntryPoint.class.getCanonicalName()
              + ", must be public");
    }
    return methodInfo;
  }

  private void generate(ExecutableElement methodInfo) {
    TypeElement clazz = (TypeElement) methodInfo.getEnclosingElement();
    boolean isJsType = utils.createTypeDescriptor(clazz.asType()).isJsType();
    String methodName =
        isJsType
            ? methodInfo.getSimpleName().toString()
            : utils.createDeclarationMethodDescriptor(methodInfo).getMangledName();
    String className = clazz.getSimpleName().toString();
    String classPkg = MoreElements.getPackage(clazz).getQualifiedName().toString();

    String source = generateNativeJsSource(methodName, className, isJsType);

    writeResource(className + ".native.js", classPkg, source);
  }

  private String generateNativeJsSource(String methodName, String className, boolean isJsType) {
    StringBuffer source = new StringBuffer();
    source.append("setTimeout(function(){");
    source.append(System.lineSeparator());
    source.append("var ep = ");

    if (isJsType) {
      source.append("new ");
      source.append(className);
      source.append("();");
    } else {
      source.append(className);
      source.append(".$create__();");
    }

    source.append(System.lineSeparator());
    source.append("    ep.");
    source.append(methodName);
    source.append("()");
    source.append(System.lineSeparator());
    source.append("}, 0);");
    source.append(System.lineSeparator());
    return source.toString();
  }
}
