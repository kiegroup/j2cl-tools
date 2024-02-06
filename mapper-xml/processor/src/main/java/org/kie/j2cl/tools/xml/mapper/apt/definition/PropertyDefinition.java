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

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlCData;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Map;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlTypeAdapter;
import org.kie.j2cl.tools.xml.mapper.api.annotation.XmlUnwrappedCollection;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;

public class PropertyDefinition extends Definition {

  public static final String DEFAULT = "##default";
  private final VariableElement property;

  protected PropertyDefinition(VariableElement property, GenerationContext context) {
    super(property.asType(), context);
    this.property = property;
  }

  public Expression getFieldDeserializer(CompilationUnit cu) {
    FieldDefinition fieldDefinition = propertyDefinitionFactory.getFieldDefinition(this);
    Expression result = fieldDefinition.getFieldDeserializer(this, cu);
    if (isCData()) {
      String value =
          PropertyType.class.getCanonicalName()
              + "."
              + (getCData().value() ? "CDATA" : "CDATA_INLINE");
      result = new MethodCallExpr(result, "setPropertyType").addArgument(new NameExpr(value));
    }
    return result;
  }

  public boolean isCData() {
    return bean.toString().equals(String.class.getCanonicalName())
        && property.getAnnotation(XmlCData.class) != null;
  }

  public XmlCData getCData() {
    return property.getAnnotation(XmlCData.class);
  }

  public Expression getFieldSerializer(CompilationUnit cu) {
    FieldDefinition fieldDefinition = propertyDefinitionFactory.getFieldDefinition(this);
    return fieldDefinition.getFieldSerializer(this, cu);
  }

  public String getPropertyName() {
    if (property.getAnnotation(XmlElementWrapper.class) != null
        && !property.getAnnotation(XmlElementWrapper.class).name().equals(DEFAULT)) {
      if (property.getAnnotation(XmlElementRefs.class) != null) {
        return property.getAnnotation(XmlElementWrapper.class).name();
      }
    }

    if (property.getAnnotation(XmlElement.class) != null
        && !property.getAnnotation(XmlElement.class).name().equals(DEFAULT)) {
      return property.getAnnotation(XmlElement.class).name();
    }

    if (property.getAnnotation(XmlAttribute.class) != null
        && !property.getAnnotation(XmlAttribute.class).name().equals(DEFAULT)) {
      return property.getAnnotation(XmlAttribute.class).name();
    }

    return property.getSimpleName().toString();
  }

  public String getNamespace() {
    if (property.getAnnotation(XmlElement.class) != null
        && !property.getAnnotation(XmlElement.class).namespace().equals(DEFAULT)) {
      return property.getAnnotation(XmlElement.class).namespace();
    }

    if (property.getAnnotation(XmlAttribute.class) != null
        && !property.getAnnotation(XmlAttribute.class).namespace().equals(DEFAULT)) {
      return property.getAnnotation(XmlAttribute.class).namespace();
    }

    XmlRootElement parent = property.getEnclosingElement().getAnnotation(XmlRootElement.class);
    if (parent != null && parent.namespace() != null && !parent.namespace().equals(DEFAULT)) {
      return parent.namespace();
    }

    XmlSchema schema = null;
    if (!context.getTypeUtils().isSimpleType(bean) && !bean.getKind().equals(TypeKind.ARRAY)) {
      schema =
          MoreElements.getPackage(MoreTypes.asTypeElement(bean)).getAnnotation(XmlSchema.class);
    }
    if (schema != null && !schema.namespace().isEmpty()) {
      return schema.namespace();
    }
    return null;
  }

  public boolean isAttribute() {
    if (getBean().getKind().equals(TypeKind.DECLARED)
        && MoreTypes.asElement(getBean()).getAnnotation(XmlTypeAdapter.class) != null) {
      return MoreTypes.asElement(getBean()).getAnnotation(XmlTypeAdapter.class).isAttribute();
    }

    if (property.getAnnotation(XmlElement.class) != null
        && property.getAnnotation(XmlAttribute.class) != null) {
      throw new GenerationException(
          "The property ["
              + property.getSimpleName()
              + "] "
              + "at ["
              + property.getEnclosingElement()
              + "] annotated with @XmlElement and @XmlAttribute, it's only possible to use one of them.");
    }

    return property.getAnnotation(XmlAttribute.class) != null;
  }

  public boolean isWrapped() {
    return property.getAnnotation(XmlElementWrapper.class) != null;
  }

  public boolean isXmlValue() {
    return property.getAnnotation(XmlValue.class) != null;
  }

  public boolean isUnWrapped() {
    if (property.getAnnotation(XmlElementRefs.class) != null) {
      if (property.getAnnotation(XmlElementWrapper.class) != null) {
        return false;
      }
      return true;
    }

    if (property.getAnnotation(XmlUnwrappedCollection.class) != null) {
      if (getBean().getKind().equals(TypeKind.ARRAY)
          || context.getTypeUtils().isCollection(getBean())
          || context.getTypeUtils().isIterable(getBean())) return true;
    }
    return false;
  }

  public boolean hasXmlSeeAlso() {
    return getXmlSeeAlso() != null;
  }

  public boolean hasXmlJavaTypeAdapter() {
    if (property.getAnnotation(XmlJavaTypeAdapter.class) != null) {
      return true;
    }
    if (MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class) != null) {

      if (MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class) != null) {
        for (XmlJavaTypeAdapter typeAdapter :
            MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class).value()) {
          try {
            typeAdapter.type();
          } catch (MirroredTypeException e) {
            if (context
                .getProcessingEnv()
                .getTypeUtils()
                .isSameType(e.getTypeMirror(), property.asType())) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
    if (property.getAnnotation(XmlJavaTypeAdapter.class) != null) {
      return property.getAnnotation(XmlJavaTypeAdapter.class);
    }
    if (MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class) != null) {

      if (MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class) != null) {
        for (XmlJavaTypeAdapter typeAdapter :
            MoreElements.getPackage(property).getAnnotation(XmlJavaTypeAdapters.class).value()) {
          try {
            typeAdapter.type();
          } catch (MirroredTypeException e) {
            if (context
                .getProcessingEnv()
                .getTypeUtils()
                .isSameType(e.getTypeMirror(), property.asType())) {
              return typeAdapter;
            }
          }
        }
      }
    }
    return null;
  }

  public TypeElement[] getXmlSeeAlso() {
    TypeMirror type = asTypeMirror();
    if (type == null) {
      return null;
    }
    return context.getBeanDefinition(type).getXmlSeeAlso();
  }

  private TypeMirror asTypeMirror() {
    if (bean.getKind().isPrimitive()) {
      return null;
    }
    TypeMirror type = bean;
    if (type.getKind().equals(TypeKind.ARRAY)) {
      ArrayType arrayType = (ArrayType) getBean();
      if (arrayType.getComponentType().getKind().equals(TypeKind.ARRAY)) {
        arrayType = (ArrayType) arrayType.getComponentType();
      }
      type = arrayType.getComponentType();
    }
    if (type.getKind().isPrimitive()) {
      return null;
    }
    return type;
  }

  public boolean hasXmlElementRefs() {
    return getXmlElementRefs() != null;
  }

  public Map<String, TypeMirror> getXmlElementRefs() {
    TypeMirror type = asTypeMirror();
    if (type != null) {
      XmlElementRefs xmlElementRefs = getProperty().getAnnotation(XmlElementRefs.class);
      if (xmlElementRefs != null) {
        return TypeUtils.getXmlElements(context, getProperty(), XmlElementRefs.class);
      }
    }
    return null;
  }

  public VariableElement getProperty() {
    return property;
  }

  public TypeElement asTypeElement() {
    return MoreTypes.asTypeElement(asTypeMirror());
  }

  public Pair<String, String> getWrapped() {
    String name =
        !property.getAnnotation(XmlElementWrapper.class).name().equals(DEFAULT)
            ? property.getAnnotation(XmlElementWrapper.class).name()
            : getPropertyName();
    String namespace =
        !property.getAnnotation(XmlElementWrapper.class).namespace().equals(DEFAULT)
            ? property.getAnnotation(XmlElementWrapper.class).namespace()
            : null;
    return new Pair<>(name, namespace);
  }

  @Override
  public String toString() {
    return "PropertyDefinition{" + "property=" + property + '}';
  }
}
