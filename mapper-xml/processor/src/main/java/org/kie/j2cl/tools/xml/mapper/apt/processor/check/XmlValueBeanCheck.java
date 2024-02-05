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

package org.kie.j2cl.tools.xml.mapper.apt.processor.check;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;

public class XmlValueBeanCheck implements BeanCheck {

  @Override
  public void check(TypeElement type, GenerationContext context) {
    Set<Element> elements =
        new HashSet<>(context.getProcessingEnv().getElementUtils().getAllMembers(type));

    long count = checkThereIsOnlyOneXmlValueField(type, elements, context);
    if (count == 1) {
      checkThereIsOnlyOneXmlValueOrXmlAttr(type, elements, context);
      checkXmlValueIsNotXmlElementWrapped(type, elements, context);
    }
  }

  private long checkThereIsOnlyOneXmlValueField(
      TypeElement type, Set<Element> elements, GenerationContext context) {
    long count = elements.stream().filter(f -> f.getAnnotation(XmlValue.class) != null).count();
    if (count > 1) {
      throw new GenerationException(
          "@XmlValue is only allowed one per class, but "
              + count
              + " properties are annotated with @XmlValue at [ "
              + type
              + "]");
    }
    return count;
  }

  private void checkThereIsOnlyOneXmlValueOrXmlAttr(
      TypeElement type, Set<Element> elements, GenerationContext context) {
    long count =
        elements.stream()
            .filter(f -> f.getKind().isField())
            .filter(f -> f.getAnnotation(XmlValue.class) == null)
            .filter(f -> f.getAnnotation(XmlAttribute.class) == null)
            .filter(f -> f.getAnnotation(XmlTransient.class) == null)
            .filter(f -> !f.getModifiers().contains(Modifier.TRANSIENT))
            .filter(f -> !f.getModifiers().contains(Modifier.STATIC))
            .count();

    if (count > 0) {
      throw new GenerationException(
          "If a class has @XmlElement property, it cannot have @XmlValue property at ["
              + type
              + "]");
    }
  }

  private void checkXmlValueIsNotXmlElementWrapped(
      TypeElement type, Set<Element> elements, GenerationContext context) {
    elements.stream()
        .filter(f -> f.getAnnotation(XmlValue.class) != null)
        .forEach(
            field -> {
              if (field.getAnnotation(XmlElementWrapper.class) != null) {
                throw new GenerationException(
                    "@XmlElementWrapper annotation is not allowed on this kind of property at ["
                        + type
                        + "]");
              }
            });
  }
}
