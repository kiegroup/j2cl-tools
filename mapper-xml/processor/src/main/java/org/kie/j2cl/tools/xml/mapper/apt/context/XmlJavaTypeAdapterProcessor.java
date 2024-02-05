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

package org.kie.j2cl.tools.xml.mapper.apt.context;

import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Arrays;
import java.util.Objects;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

public class XmlJavaTypeAdapterProcessor {

  private GenerationContext context;

  XmlJavaTypeAdapterProcessor(GenerationContext context) {
    this.context = context;
  }

  void process() {
    context
        .getRoundEnvironment()
        .getElementsAnnotatedWith(XmlJavaTypeAdapter.class)
        .forEach(this::process);

    context.getRoundEnvironment().getElementsAnnotatedWith(XmlJavaTypeAdapters.class).stream()
        .map(anno -> anno.getAnnotation(XmlJavaTypeAdapters.class))
        .map(XmlJavaTypeAdapters::value)
        .forEach(a -> Arrays.stream(a).forEach(this::process));
  }

  private void process(Element typeElement) {
    process(typeElement.getAnnotation(XmlJavaTypeAdapter.class));
  }

  private void process(XmlJavaTypeAdapter typeAdapter) {
    process(getUnmarshal(typeAdapter));
  }

  private void process(ExecutableElement decl) {
    VariableElement parameterElement = decl.getParameters().get(0);
    context.addBeanDefinition(MoreTypes.asTypeElement(parameterElement.asType()));
  }

  private ExecutableElement getUnmarshal(XmlJavaTypeAdapter typeAdapter) {
    return ElementFilter.methodsIn(
            MoreTypes.asTypeElement(Objects.requireNonNull(getAnnotation(typeAdapter)))
                .getEnclosedElements())
        .stream()
        .filter(field -> field.getSimpleName().toString().equals("unmarshal"))
        .findFirst()
        .get();
  }

  private TypeMirror getAnnotation(XmlJavaTypeAdapter typeAdapter) {
    try {
      typeAdapter.value();
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
    return null;
  }
}
