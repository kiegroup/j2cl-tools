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
package org.kie.j2cl.tools.xml.mapper.apt.processor;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;
import org.kie.j2cl.tools.xml.mapper.apt.generator.MapperGenerator;
import org.kie.j2cl.tools.xml.mapper.apt.logger.TreeLogger;
import org.kie.j2cl.tools.xml.mapper.apt.processor.check.BeanCheck;
import org.kie.j2cl.tools.xml.mapper.apt.processor.check.XmlValueBeanCheck;

public class BeanProcessor {

  private final GenerationContext context;
  private final TreeLogger logger;
  private final Set<TypeElement> annotatedBeans;
  private final Set<TypeElement> beans = new HashSet<>();
  private final TypeUtils typeUtils;
  private final MapperGenerator mapperGenerator;

  private final Set<BeanCheck> beanChecks =
      new HashSet<BeanCheck>() {
        {
          add(new XmlValueBeanCheck());
        }
      };

  public BeanProcessor(
      GenerationContext context, TreeLogger logger, Set<TypeElement> annotatedBeans) {
    this.context = context;
    this.logger = logger;
    this.annotatedBeans = annotatedBeans;
    this.typeUtils = context.getTypeUtils();
    this.mapperGenerator = new MapperGenerator(context, logger);
  }

  public void process() {
    annotatedBeans.forEach(this::processBean);
    beans.forEach(context::addBeanDefinition);
    context.getBeans().stream()
        .filter(elm -> context.getTypeRegistry().get(elm.getElement().toString()) == null)
        .filter(
            elm ->
                !MoreTypes.asTypeElement(elm.getBean()).getModifiers().contains(Modifier.ABSTRACT))
        .filter(type -> !hasXmlAdapter(type.getElement()))
        .forEach(mapperGenerator::generate);
  }

  private void processBean(TypeElement bean) {
    if (!beans.contains(bean)) {
      beans.add(checkBean(bean));
      context.getTypeUtils().getAllFieldsIn(bean).forEach(this::processField);
    }
  }

  private void processField(VariableElement field) {
    if (checkField(field)) {
      // Ensure the serializer/deserializer is generated for the fields concrete type, considering
      // XmlElement and XmlElementRef types
      TypeMirror typeMirror = typeUtils.getTypeMirror(field).orElse(field.asType());
      checkTypeAndAdd(typeMirror);
    }
  }

  private void checkTypeAndAdd(TypeMirror type) {
    if (context
            .getTypeRegistry()
            .get(context.getProcessingEnv().getTypeUtils().erasure(type).toString())
        == null) {
      if (type.getKind().equals(TypeKind.ARRAY)) {
        ArrayType arrayType = (ArrayType) type;

        if (!context.getTypeUtils().isSimpleType(arrayType.getComponentType())) {
          processBean(typeUtils.toTypeElement(arrayType.getComponentType()));
        }
      } else if (MoreTypes.isType(type)
          && !MoreTypes.asElement(type).getKind().equals(ElementKind.ENUM)) {
        processBean(typeUtils.toTypeElement(type));
      }
    }

    if (context.getTypeUtils().isCollection(type)) {
      DeclaredType collection = (DeclaredType) type;
      collection.getTypeArguments().forEach(this::checkTypeAndAdd);
    }

    if (context.getTypeUtils().isMap(type)) {
      DeclaredType collection = (DeclaredType) type;
      collection.getTypeArguments().forEach(this::checkTypeAndAdd);
    }
  }

  private boolean checkField(VariableElement field) {
    if (field.getModifiers().contains(Modifier.STATIC)
        || field.getModifiers().contains(Modifier.TRANSIENT)
        || field.getAnnotation(XmlTransient.class) != null
        || field.getModifiers().contains(Modifier.FINAL)
        || hasXmlAdapter(field)) {
      return false;
    }
    if (!field.getModifiers().contains(Modifier.PRIVATE)
        || typeUtils.hasGetter(field) && typeUtils.hasSetter(field)) {
      return true;
    }

    if (!typeUtils.hasGetter(field)) {
      throw new GenerationException(
          String.format(
              "Unable to find suitable getter for [%s] in [%s].",
              field.getSimpleName(), field.getEnclosingElement()));
    }

    if (!typeUtils.hasSetter(field)) {
      throw new GenerationException(
          String.format(
              "Unable to find suitable setter for [%s] in [%s]",
              field.getSimpleName(), field.getEnclosingElement()));
    }

    throw new GenerationException(
        String.format(
            "Unable to process [%s] in [%s]", field.getSimpleName(), field.getEnclosingElement()));
  }

  private boolean hasXmlAdapter(Element field) {
    if (field.getAnnotation(XmlJavaTypeAdapter.class) != null) {
      return true;
    }

    if (MoreElements.getPackage(field).getAnnotation(XmlJavaTypeAdapters.class) != null) {
      for (XmlJavaTypeAdapter typeAdapter :
          MoreElements.getPackage(field).getAnnotation(XmlJavaTypeAdapters.class).value()) {
        try {
          typeAdapter.type();
        } catch (MirroredTypeException e) {
          return context
              .getProcessingEnv()
              .getTypeUtils()
              .isSameType(e.getTypeMirror(), field.asType());
        }
      }
    }
    return false;
  }

  private TypeElement checkBean(TypeElement type) {
    if (type.getModifiers().contains(Modifier.PRIVATE)) {
      throw new GenerationException("A @XMLMapper bean [" + type + "] must be public");
    }

    if (type.getEnclosingElement().getKind().isClass()
        && !type.getModifiers().contains(Modifier.STATIC)) {
      throw new GenerationException("A @XMLMapper bean [" + type + "] must be static");
    }

    List<ExecutableElement> constructors = ElementFilter.constructorsIn(type.getEnclosedElements());
    if (!constructors.isEmpty()) {
      long nonArgConstructorCount =
          constructors.stream()
              .filter(constr -> !constr.getModifiers().contains(Modifier.PRIVATE))
              .filter(constr -> constr.getParameters().isEmpty())
              .count();
      if (nonArgConstructorCount != 1) {
        throw new GenerationException(
            "A @XMLMapper bean [" + type + "] must have a non-private non-arg constructor");
      }
    }
    beanChecks.forEach(check -> check.check(type, context));

    return type;
  }
}
