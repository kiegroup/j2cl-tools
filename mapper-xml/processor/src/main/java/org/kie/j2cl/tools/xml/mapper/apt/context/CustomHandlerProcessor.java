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

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.ElementFilter;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlTypeAdapter;
import org.kie.j2cl.tools.xml.mapper.api.custom.CustomXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.custom.CustomXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;

public class CustomHandlerProcessor {

  private GenerationContext context;

  CustomHandlerProcessor(GenerationContext context) {
    this.context = context;
  }

  void process(Set<? extends Element> elements) {
    elements.stream()
        .filter(elm -> elm.getKind().equals(ElementKind.CLASS))
        .map(MoreElements::asType)
        .forEach(this::process);
  }

  private void process(TypeElement element) {
    checkClass(element);
    checkConstructor(element);

    XmlTypeAdapter xmlTypeAdapter = element.getAnnotation(XmlTypeAdapter.class);
    TypeElement ser = getSerializer(xmlTypeAdapter);
    TypeElement deser = getDeserializer(xmlTypeAdapter);

    checkExtends(element, ser, deser);

    if (ser != null) {
      context.getTypeRegistry().registerSerializer(element.getQualifiedName().toString(), ser);
    }
    if (deser != null) {
      context.getTypeRegistry().registerDeserializer(element.getQualifiedName().toString(), deser);
    }
  }

  private void checkClass(TypeElement type) {
    if (!type.getModifiers().contains(Modifier.PUBLIC)) {
      throw new GenerationException(type + " must be PUBLIC");
    }
    if (type.getModifiers().contains(Modifier.ABSTRACT)) {
      throw new GenerationException(type + " must not be ABSTRACT");
    }
  }

  private void checkConstructor(TypeElement type) {
    List<ExecutableElement> constructors = ElementFilter.constructorsIn(type.getEnclosedElements());
    if (!constructors.isEmpty()) {
      long nonArgConstructorCount =
          constructors.stream()
              .filter(constr -> constr.getModifiers().contains(Modifier.PUBLIC))
              .filter(constr -> constr.getParameters().isEmpty())
              .count();
      if (nonArgConstructorCount != 1) {
        throw new GenerationException(
            "A @XMLMapper bean [" + type + "] must have a non-private non-arg constructor");
      }
    }
  }

  private TypeElement getSerializer(XmlTypeAdapter xmlTypeAdapter) {
    try {
      xmlTypeAdapter.serializer();
    } catch (MirroredTypeException e) {
      return MoreTypes.asTypeElement(e.getTypeMirror());
    }
    return null;
  }

  private TypeElement getDeserializer(XmlTypeAdapter xmlTypeAdapter) {
    try {
      xmlTypeAdapter.deserializer();
    } catch (MirroredTypeException e) {
      return MoreTypes.asTypeElement(e.getTypeMirror());
    }
    return null;
  }

  private void checkExtends(TypeElement handler, TypeElement ser, TypeElement deser) {
    if (ser != null && !context.getTypeUtils().isAssignableFrom(ser, CustomXMLSerializer.class)) {
      throw new GenerationException(
          handler + " must extends " + CustomXMLSerializer.class.getCanonicalName());
    }

    if (deser != null
        && !context.getTypeUtils().isAssignableFrom(deser, CustomXMLDeserializer.class)) {
      throw new GenerationException(
          handler + " must extends " + CustomXMLDeserializer.class.getCanonicalName());
    }
  }
}
