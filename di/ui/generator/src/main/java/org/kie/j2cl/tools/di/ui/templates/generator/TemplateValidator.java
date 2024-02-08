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

package org.kie.j2cl.tools.di.ui.templates.generator;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.kie.j2cl.tools.di.apt.exception.UnableToCompleteException;
import org.kie.j2cl.tools.di.apt.generator.context.IOCContext;
import org.kie.j2cl.tools.di.apt.validation.Check;
import org.kie.j2cl.tools.di.apt.validation.Validator;
import org.kie.j2cl.tools.di.core.IsElement;
import org.kie.j2cl.tools.di.ui.templates.client.annotation.Templated;

public class TemplateValidator extends Validator<TypeElement> {

  private final TypeMirror isElement;
  private final Types types;
  private final Elements elements;


  public TemplateValidator(IOCContext context) {
    super(context);

    types = context.getGenerationContext().getTypes();
    elements = context.getGenerationContext().getElements();

    isElement = types.erasure(elements.getTypeElement(IsElement.class.getCanonicalName()).asType());

    addCheck(new Check<>() {
      @Override
      public void check(TypeElement element) throws UnableToCompleteException {
        if (element.getAnnotation(Templated.class) == null) {
          log(element, "Templated class must be annotated with @Templated");
        }
      }
    });
    addCheck(new Check<>() {
      @Override
      public void check(TypeElement element) throws UnableToCompleteException {
        if (!element.getKind().isClass()) {
          log(element, "Element, annotated with @Templated, must be a class [" + element + "]");
        }
      }
    });

    addCheck(new Check<>() {
      @Override
      public void check(TypeElement element) throws UnableToCompleteException {
        if (element.getModifiers().contains(javax.lang.model.element.Modifier.ABSTRACT)) {
          log(element, "Class, annotated with @Templated, must not be abstract [" + element + "]");
        }
      }
    });


    addCheck(new Check<>() {
      @Override
      public void check(TypeElement element) throws UnableToCompleteException {
        if (!types.isSubtype(element.asType(), isElement)) {
          log(element, "Templated class must implements IsElement [" + element + "]");
        }
      }
    });
  }
}
