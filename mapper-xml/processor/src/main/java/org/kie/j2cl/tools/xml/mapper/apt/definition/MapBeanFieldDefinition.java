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
import java.util.function.Function;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.deser.map.MapXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.map.MapXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;

public class MapBeanFieldDefinition extends FieldDefinition {

  private DeclaredType declaredType;

  protected MapBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
    declaredType = MoreTypes.asDeclared(property);
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    if (declaredType.getTypeArguments().size() != 2) {
      throw new GenerationException(
          declaredType.toString() + " must have type args [" + bean + "]");
    }
    return new MethodCallExpr(
            new NameExpr(MapXMLDeserializer.class.getCanonicalName()), "newInstance")
        .addArgument(
            generateXMLDeserializerFactory(
                field,
                declaredType.getTypeArguments().get(0),
                declaredType.getTypeArguments().get(0).toString(),
                cu))
        .addArgument(
            generateXMLDeserializerFactory(
                field,
                declaredType.getTypeArguments().get(1),
                declaredType.getTypeArguments().get(1).toString(),
                cu));
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(Function.class);
    if (declaredType.getTypeArguments().size() != 2) {
      throw new GenerationException(
          declaredType.toString() + " must have type args [" + field.getPropertyName() + "]");
    }
    return new MethodCallExpr(
            new NameExpr(MapXMLSerializer.class.getCanonicalName()), "newInstance")
        .addArgument(
            generateXMLSerializerFactory(field, declaredType.getTypeArguments().get(0), "?", cu))
        .addArgument(
            generateXMLSerializerFactory(field, declaredType.getTypeArguments().get(1), "?", cu))
        .addArgument(new StringLiteralExpr(field.getPropertyName()));
  }

  @Override
  public String toString() {
    return "MapBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
