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
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.util.function.Function;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.ArrayXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.deser.array.dd.Array2dXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.ArrayXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.array.dd.Array2dXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public class ArrayBeanFieldDefinition extends FieldDefinition {

  protected ArrayBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(ArrayXMLDeserializer.ArrayCreator.class);
    cu.addImport(ArrayXMLDeserializer.class);

    ArrayType array = (ArrayType) bean;
    TypeMirror componentType =
        context.getTypeUtils().getTypeMirror(field).orElse(array.getComponentType());
    String arrayType = componentType.toString();
    if (componentType.getKind().isPrimitive()) {
      arrayType =
          context
              .getProcessingEnv()
              .getTypeUtils()
              .boxedClass((PrimitiveType) componentType)
              .getSimpleName()
              .toString();
    } else if (componentType.getKind().equals(TypeKind.ARRAY)) {
      ArrayType array2d = (ArrayType) componentType;
      if (array2d.getComponentType().getKind().isPrimitive()) {
        arrayType =
            context
                    .getProcessingEnv()
                    .getTypeUtils()
                    .boxedClass((PrimitiveType) array2d.getComponentType())
                    .getSimpleName()
                    .toString()
                + "[]";
      } else {
        cu.addImport(Array2dXMLDeserializer.class);
        cu.addImport(Array2dXMLDeserializer.Array2dCreator.class);
        arrayType = array2d.getComponentType().toString();

        ClassOrInterfaceType typeOf =
            new ClassOrInterfaceType()
                .setName(Array2dXMLDeserializer.Array2dCreator.class.getSimpleName())
                .setTypeArguments(new ClassOrInterfaceType().setName(arrayType));

        return maybeXmlUnwrappedCollection(
            field,
            new MethodCallExpr(
                    new NameExpr(Array2dXMLDeserializer.class.getSimpleName()), "newInstance")
                .addArgument(
                    generateXMLDeserializerFactory(
                        field,
                        array2d.getComponentType(),
                        array2d.getComponentType().toString(),
                        cu))
                .addArgument(
                    new CastExpr()
                        .setType(typeOf)
                        .setExpression(
                            new NameExpr(
                                "(first, second) -> new " + arrayType + "[first][second]"))));
      }
    }

    ClassOrInterfaceType typeOf =
        new ClassOrInterfaceType()
            .setName(ArrayXMLDeserializer.ArrayCreator.class.getSimpleName())
            .setTypeArguments(new ClassOrInterfaceType().setName(arrayType));

    return maybeXmlUnwrappedCollection(
        field,
        new MethodCallExpr(new NameExpr(ArrayXMLDeserializer.class.getSimpleName()), "newInstance")
            .addArgument(
                generateXMLDeserializerFactory(field, componentType, componentType.toString(), cu))
            .addArgument(
                new CastExpr()
                    .setType(typeOf)
                    .setExpression(
                        new NameExpr(
                            context.getProcessingEnv().getTypeUtils().erasure(componentType)
                                + "[]::new"))));
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(ArrayXMLSerializer.class);
    cu.addImport(Array2dXMLSerializer.class);
    cu.addImport(Function.class);

    ArrayType array = (ArrayType) getBean();
    String serializer;
    TypeMirror type;
    if (array.getComponentType().getKind().equals(TypeKind.ARRAY)) {
      serializer = Array2dXMLSerializer.class.getSimpleName();
      ArrayType array2d = (ArrayType) array.getComponentType();
      type = array2d.getComponentType();
    } else {
      serializer = ArrayXMLSerializer.class.getSimpleName();
      type = array.getComponentType();
    }
    return maybeHasNamespacePrefix(
        field,
        maybeXmlUnwrappedCollection(
            field,
            new MethodCallExpr(new NameExpr(serializer), "getInstance")
                .addArgument(generateXMLSerializerFactory(field, type, type.toString(), cu))
                .addArgument(new StringLiteralExpr(field.getPropertyName()))));
  }

  private Expression maybeHasNamespacePrefix(PropertyDefinition field, Expression method) {
    if (field.getNamespace() != null) {
      method =
          new MethodCallExpr(method, "setNamespace")
              .addArgument(new StringLiteralExpr(field.getNamespace()));
    }
    return method;
  }

  private Expression maybeXmlUnwrappedCollection(
      PropertyDefinition element, Expression expression) {
    // if (element.isUnWrapped()) {
    return new MethodCallExpr(expression, "setUnWrapCollections");
    // }

    // return expression;
  }

  @Override
  public String toString() {
    return "ArrayBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
