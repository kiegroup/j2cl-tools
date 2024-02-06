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
package org.kie.j2cl.tools.xml.mapper.apt.definition;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlCData;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.annotation.TargetNamespace;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlXsiType;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;

public class BeanDefinition extends Definition {

  private final TypeElement element;
  private final XmlRootElement xmlRootElement;
  private final XmlType xmlType;
  private final XmlSchema xmlSchema;
  private final XmlAccessorType xmlAccessorType;
  private final XmlSeeAlso xmlSeeAlso;
  private Set<PropertyDefinition> fields = new LinkedHashSet<>();
  private Class<? extends Annotation>[] allowedPropertyAnnotations =
      new Class[] {XmlAttribute.class, XmlCData.class, XmlElement.class};
  private static final String DEFAULT = "##default";

  public BeanDefinition(TypeElement element, GenerationContext context) {
    super(element.asType(), context);
    this.element = element;

    xmlRootElement = getElement().getAnnotation(XmlRootElement.class);
    xmlType = getElement().getAnnotation(XmlType.class);
    xmlAccessorType = getElement().getAnnotation(XmlAccessorType.class);
    xmlSchema = MoreElements.getPackage(element).getAnnotation(XmlSchema.class);
    xmlSeeAlso = getElement().getAnnotation(XmlSeeAlso.class);

    /** Since we can't use reflection we ll process all this cases as default */
    loadProperties(
        !(xmlAccessorType == null
            || xmlAccessorType.value().equals(XmlAccessType.FIELD)
            || xmlAccessorType.value().equals(XmlAccessType.PROPERTY)
            || xmlAccessorType.value().equals(XmlAccessType.PUBLIC_MEMBER)));
  }

  public TypeElement getElement() {
    return element;
  }

  private void loadProperties(boolean annotated) {
    Predicate<VariableElement> isAnnotated =
        field -> {
          if (annotated) {
            for (Class<? extends Annotation> anno : allowedPropertyAnnotations) {
              if (field.getAnnotation(anno) != null) {
                return true;
              }
            }
            return false;
          }
          return true;
        };

    Stream<PropertyDefinition> stream = getPropertyDefinitionAsStream(isAnnotated);

    if (xmlType == null || Arrays.stream(xmlType.propOrder()).allMatch(p -> p.equals(""))) {
      stream.forEach(elm -> fields.add(elm));
    } else {
      Set<String> propOrder = new LinkedHashSet<>(Arrays.asList(xmlType.propOrder()));
      Map<String, PropertyDefinition> temp =
          stream.collect(Collectors.toMap(PropertyDefinition::getPropertyName, x -> x));
      for (String s : propOrder) {
        if (temp.containsKey(s)) {
          fields.add(temp.get(s));
        } else if (temp.values().stream()
            .anyMatch(p -> p.getProperty().getSimpleName().toString().equals(s))) {
          temp.entrySet().stream()
              .filter(e -> e.getValue().getProperty().getSimpleName().toString().equals(s))
              .findFirst()
              .ifPresent(e -> fields.add(e.getValue()));
        } else {
          throw new GenerationException(
              "Property "
                  + s
                  + " appears in @XmlType.propOrder, but no such property exists."
                  + getElement());
        }
      }
      temp.entrySet().stream()
          .filter(v -> !propOrder.contains(v.getKey()))
          .forEach(v -> fields.add(v.getValue()));
    }
  }

  private Stream<PropertyDefinition> getPropertyDefinitionAsStream(
      Predicate<VariableElement> isAnnotated) {
    return context.getTypeUtils().getAllFieldsIn(element).stream()
        .filter(field -> !field.getModifiers().contains(Modifier.STATIC))
        .filter(field -> !field.getModifiers().contains(Modifier.FINAL))
        .filter(field -> !field.getModifiers().contains(Modifier.TRANSIENT))
        .filter(field -> field.getAnnotation(XmlTransient.class) == null)
        .filter(isAnnotated)
        .map(
            field -> {
              PropertyDefinition propertyDefinition = new PropertyDefinition(field, context);
              if (TypeUtils.hasTypeParameter(field.asType())) {
                TypeMirror typeMirror =
                    context
                        .getProcessingEnv()
                        .getTypeUtils()
                        .asMemberOf((DeclaredType) element.asType(), field);
                propertyDefinition.setBean(typeMirror);
              }
              return propertyDefinition;
            });
  }

  public Set<PropertyDefinition> getFields() {
    return fields;
  }

  @Override
  public int hashCode() {
    return Objects.hash(element);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BeanDefinition)) {
      return false;
    }
    BeanDefinition that = (BeanDefinition) o;
    return Objects.equals(element, that.element);
  }

  @Override
  public String toString() {
    return "BeanDefinition{" + "element=" + element + '}';
  }

  public String getXmlRootElement() {
    if (xmlRootElement != null && !xmlRootElement.name().equals(DEFAULT)) {
      return xmlRootElement.name();
    }
    return getElement().getSimpleName().toString();
  }

  public List<Pair<String, String>> getXmlNs() {
    List<Pair<String, String>> result = new ArrayList<>();
    if (xmlSchema != null && !xmlSchema.namespace().isEmpty()) {
      result.add(new Pair<>(null, xmlSchema.namespace()));
    } else if (xmlRootElement != null && !xmlRootElement.namespace().equals(DEFAULT)) {
      result.add(new Pair<>(null, xmlRootElement.namespace()));
    }

    if (xmlSchema != null && xmlSchema.xmlns().length > 0) {
      for (XmlNs xmln : xmlSchema.xmlns()) {
        result.add(new Pair<>(xmln.prefix(), xmln.namespaceURI()));
      }
    }

    return result;
  }

  public String getNamespace() {
    if (xmlType != null
        && !xmlType.namespace().equals(DEFAULT)
        && xmlRootElement != null
        && !xmlRootElement.namespace().equals(DEFAULT)) {
      throw new GenerationException(
          "Apply one namespace dicloration at a time. @XmlType and @XmlRootElement contain namespace declaration at "
              + getElement());
    }

    if (xmlRootElement != null && !xmlRootElement.namespace().equals(DEFAULT)) {
      return xmlRootElement.namespace();
    }

    if (xmlType != null && !xmlType.namespace().equals(DEFAULT)) {
      return xmlType.namespace();
    }

    if (xmlSchema != null && !xmlSchema.namespace().isEmpty()) {
      return xmlSchema.namespace();
    }

    return null;
  }

  public String getSchemaLocation() {
    if (xmlSchema != null && !xmlSchema.location().equals("##generate")) {
      return xmlSchema.location();
    }
    return null;
  }

  public String[] getXsiType() {
    if (element.getAnnotation(XmlXsiType.class) != null) {
      return element.getAnnotation(XmlXsiType.class).value();
    }
    return null;
  }

  public XmlAccessType getAccessorType() {
    if (this.xmlAccessorType == null) {
      return XmlAccessType.PROPERTY;
    }

    return this.xmlAccessorType.value();
  }

  public String getSimpleName() {
    return getElement().getSimpleName().toString();
  }

  public String getQualifiedName() {
    return getElement().getQualifiedName().toString();
  }

  public Pair<String, String> getTargetNamespace() {
    TargetNamespace targetNamespace = getElement().getAnnotation(TargetNamespace.class);
    if (targetNamespace != null) {
      return new Pair<>(targetNamespace.prefix(), targetNamespace.namespace());
    }
    return null;
  }

  public TypeElement[] getXmlSeeAlso() {
    if (xmlSeeAlso != null) {
      try {
        xmlSeeAlso.value();
      } catch (MirroredTypesException e) {
        TypeElement[] result = new TypeElement[e.getTypeMirrors().size()];
        for (int i = 0; i < e.getTypeMirrors().size(); i++) {
          result[i] = MoreTypes.asTypeElement(e.getTypeMirrors().get(i));
        }
        return result;
      }
    }
    return null;
  }
}
