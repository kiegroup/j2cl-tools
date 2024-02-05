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
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.Map;
import java.util.function.Function;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.Inheritance;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public class DefaultBeanFieldDefinition extends FieldDefinition {

  private final TypeUtils typeUtils;

  protected DefaultBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
    this.typeUtils = context.getTypeUtils();
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    if (field != null && isPolymorphic(field)) {
      Pair<Class, Map<String, TypeMirror>> pair = maybePolymorphicType(field, bean);
      String inheritance =
          pair.key.equals(XmlElementRefs.class) ? "xsiTagChooser" : "xsiTypeChooser";

      // TODO refactoring, single field, annotated with @XmlElementRefs and @XmlElementWrapper
      String arg = "reader";
      if ((!context.getTypeUtils().isCollection(field.getBean())
              && !context.getTypeUtils().isIterable(field.getBean())
              && !TypeUtils.isArray(field.getBean()))
          && field.getProperty().getAnnotation(XmlElementRefs.class) != null
          && field.getProperty().getAnnotation(XmlElementWrapper.class) != null) {
        arg = "nextTag.apply(reader)";
      }

      MethodCallExpr theCall =
          new MethodCallExpr(new NameExpr(inheritance), "apply").addArgument(arg);

      return new MethodCallExpr(
              generateXMLDeserializerFactory(field, bean, bean.toString(), cu, pair), "apply")
          .addArgument(theCall);
    }

    // Ensure the generated deserializer is for the fields concrete type, considering XmlElement and
    // XmlElementRef types
    TypeMirror typeMirror = typeUtils.getTypeMirror(field).orElse(getBean());
    return new ObjectCreationExpr()
        .setType(
            new ClassOrInterfaceType().setName(typeUtils.canonicalDeserializerName(typeMirror)));
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    if (isPolymorphic(field)) {
      cu.addImport(Inheritance.class);
      cu.addImport(Function.class);

      return new MethodCallExpr(
              generateXMLSerializerFactory(field, bean, bean.toString(), cu), "apply")
          .addArgument("value");
    }

    // Ensure the generated serializer is for the fields concrete type, considering XmlElement and
    // XmlElementRef types
    TypeMirror typeMirror = typeUtils.getTypeMirror(field).orElse(getBean());
    return new ObjectCreationExpr()
        .setType(new ClassOrInterfaceType().setName(typeUtils.canonicalSerializerName(typeMirror)));
  }

  private boolean isPolymorphic(PropertyDefinition field) {
    return field != null
        && (context.getBeanDefinition(getBean()).getXmlSeeAlso() != null
            || field.getProperty().getAnnotation(XmlElements.class) != null
            || field.getProperty().getAnnotation(XmlElementRefs.class) != null);
  }

  @Override
  public String toString() {
    return "DefaultBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
