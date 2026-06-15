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
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.auto.common.MoreTypes;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.map.MapYAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.map.MapYAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.context.GenerationContext;
import org.kie.j2cl.tools.yaml.mapper.exception.GenerationException;

/** @author Dmitrii Tikhomirov Created by treblereel 4/1/20 */
public class MapBeanFieldDefinition extends FieldDefinition {

  private TypeMirror STRING_TYPE;

  protected MapBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
    STRING_TYPE =
        context
            .getProcessingEnv()
            .getElementUtils()
            .getTypeElement(String.class.getCanonicalName())
            .asType();
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    DeclaredType declaredType = MoreTypes.asDeclared(bean);
    if (declaredType.getTypeArguments().size() != 2) {
      throw new GenerationException(
          declaredType.toString() + " must have type args [" + bean + "]");
    }
    return new MethodCallExpr(
            new NameExpr(MapYAMLDeserializer.class.getCanonicalName()), "newInstance")
        .addArgument(
            propertyDefinitionFactory
                .getFieldDefinition(declaredType.getTypeArguments().get(1))
                .getFieldDeserializer(field, cu));
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    DeclaredType declaredType = MoreTypes.asDeclared(getBean());
    if (declaredType.getTypeArguments().size() != 2) {
      throw new GenerationException(
          declaredType.toString()
              + " must have type args ["
              + field.getProperty().toString()
              + "]");
    }

    if (!context
        .getProcessingEnv()
        .getTypeUtils()
        .isSameType(declaredType.getTypeArguments().get(0), STRING_TYPE)) {
      throw new GenerationException(
          "Only Maps with String keys are supported for YAML serialization. Problematic type: "
              + declaredType.getTypeArguments().get(0).toString()
              + " at "
              + field.getBean());
    }

    if (context.getTypeUtils().isObject(declaredType.getTypeArguments().get(1))) {
      throw new GenerationException(
          "Map with Object values are not supported for YAML serialization. Problematic type: "
              + declaredType.getTypeArguments().get(1).toString()
              + " at "
              + field.getBean());
    }

    PropertyDefinition valuePropertyDefinition =
        new PropertyDefinition(
            MoreTypes.asElement(declaredType.getTypeArguments().get(1)), context);

    return new MethodCallExpr(
            new NameExpr(MapYAMLSerializer.class.getCanonicalName()), "newInstance")
        .addArgument(
            propertyDefinitionFactory
                .getFieldDefinition(declaredType.getTypeArguments().get(1))
                .getFieldSerializer(valuePropertyDefinition, cu))
        .addArgument(new StringLiteralExpr(field.getPropertyName()));
  }

  @Override
  public String toString() {
    return "MapBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
