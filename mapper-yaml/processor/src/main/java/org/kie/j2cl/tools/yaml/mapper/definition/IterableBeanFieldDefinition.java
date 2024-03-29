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

package org.kie.j2cl.tools.yaml.mapper.definition;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.google.auto.common.MoreTypes;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;
import org.kie.j2cl.tools.yaml.mapper.context.GenerationContext;

public class IterableBeanFieldDefinition extends FieldDefinition {

  protected IterableBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    TypeElement serializer =
        context
            .getTypeRegistry()
            .getDeserializer(context.getProcessingEnv().getTypeUtils().erasure(bean));

    cu.addImport(serializer.getQualifiedName().toString());

    MethodCallExpr method =
        new MethodCallExpr(new NameExpr(serializer.getSimpleName().toString()), "newInstance");
    if (field.hasYamlTypeSerializer()) {
      method.addArgument(
          field.getFieldYamlTypeDeserializerCreationExpr(
              field.getProperty().getAnnotation(YamlTypeDeserializer.class)));
    } else {
      MoreTypes.asDeclared(bean)
          .getTypeArguments()
          .forEach(
              param ->
                  method.addArgument(
                      propertyDefinitionFactory
                          .getFieldDefinition(param)
                          .getFieldDeserializer(field, cu)));
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
    if (field.hasYamlTypeSerializer()) {
      method.addArgument(
          field.getFieldYamlTypeSerializerCreationExpr(
              field.getProperty().getAnnotation(YamlTypeSerializer.class)));
    } else {
      for (TypeMirror param : MoreTypes.asDeclared(getBean()).getTypeArguments()) {
        method.addArgument(
            propertyDefinitionFactory.getFieldDefinition(param).getFieldSerializer(field, cu));
      }
    }
    return method;
  }

  @Override
  public String toString() {
    return "IterableBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
