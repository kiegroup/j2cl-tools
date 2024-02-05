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
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlElementRefs;
import java.util.Map;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.Inheritance;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public class IterableBeanFieldDefinition extends FieldDefinition {

  protected IterableBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    TypeElement deserializer =
        context
            .getTypeRegistry()
            .getDeserializer(context.getProcessingEnv().getTypeUtils().erasure(bean));

    cu.addImport(deserializer.getQualifiedName().toString());

    MethodCallExpr method =
        new MethodCallExpr(new NameExpr(deserializer.getSimpleName().toString()), "newInstance");

    Pair<Class, Map<String, TypeMirror>> maybePolymorphicType = maybePolymorphicType(field, bean);
    String inheritance =
        maybePolymorphicType.key.equals(XmlElementRefs.class)
            ? "Inheritance.TAG"
            : "Inheritance.XSI";
    TypeMirror typeMirror =
        context
            .getTypeUtils()
            .getTypeMirror(field)
            .orElse(MoreTypes.asDeclared(bean).getTypeArguments().get(0));
    if (!maybePolymorphicType.value.isEmpty()) {
      cu.addImport(Inheritance.class);
      method.addArgument(
          generateXMLDeserializerFactory(
              field, typeMirror, typeMirror.toString(), cu, maybePolymorphicType));
      method = new MethodCallExpr(method, "setInheritanceType").addArgument(inheritance);
    } else {
      method.addArgument(
          generateXMLDeserializerFactory(field, typeMirror, typeMirror.toString(), cu));
    }
    if (field.isUnWrapped()) {
      return new MethodCallExpr(method, "setUnWrapCollections");
    }
    return method;
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    TypeElement serializer =
        context
            .getTypeRegistry()
            .getSerializer(context.getProcessingEnv().getTypeUtils().erasure(getBean()));

    MethodCallExpr method =
        new MethodCallExpr(new NameExpr(serializer.getQualifiedName().toString()), "newInstance");
    for (TypeMirror param : MoreTypes.asDeclared(getBean()).getTypeArguments()) {
      method.addArgument(generateXMLSerializerFactory(field, param, "?", cu));
    }
    method.addArgument(new StringLiteralExpr(field.getPropertyName()));
    if (field.isUnWrapped()) {
      method = new MethodCallExpr(method, "setUnWrapCollections");
    }
    if (field.getNamespace() != null) {
      method =
          new MethodCallExpr(method, "setNamespace")
              .addArgument(new StringLiteralExpr(field.getNamespace()));
    }
    return method;
  }

  @Override
  public String toString() {
    return "IterableBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
