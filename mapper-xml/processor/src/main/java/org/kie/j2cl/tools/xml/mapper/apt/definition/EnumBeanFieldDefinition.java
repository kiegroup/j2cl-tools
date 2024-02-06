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
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlEnumValue;
import java.util.function.Function;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.deser.EnumXMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.EnumXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public class EnumBeanFieldDefinition extends FieldDefinition {

  protected EnumBeanFieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(EnumXMLDeserializer.class);
    cu.addImport(Function.class);
    cu.addImport(MoreTypes.asTypeElement(bean).getQualifiedName().toString());

    MethodCallExpr expr =
        new MethodCallExpr(new NameExpr(EnumXMLDeserializer.class.getSimpleName()), "newInstance")
            .addArgument(MoreTypes.asTypeElement(bean).getSimpleName().toString() + ".class");

    NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();
    NodeList<Type> typeArguments = new NodeList<>();
    typeArguments.add(new ClassOrInterfaceType().setName("String"));
    typeArguments.add(new ClassOrInterfaceType().setName(field.getBean().toString()));

    ClassOrInterfaceType type = new ClassOrInterfaceType().setName("Function");
    type.setTypeArguments(typeArguments);

    ObjectCreationExpr function = new ObjectCreationExpr().setType(type);
    function.setAnonymousClassBody(anonymousClassBody);

    MethodDeclaration apply = new MethodDeclaration();
    apply.setModifiers(Modifier.Keyword.PUBLIC);
    apply.addAnnotation(Override.class);
    apply.setName("apply");
    apply.setType(field.getBean().toString());
    apply.addParameter("String", "value");

    anonymousClassBody.add(apply);

    for (Element enumConstant : MoreTypes.asTypeElement(bean).getEnclosedElements()) {
      if (enumConstant.getKind().equals(ElementKind.ENUM_CONSTANT)) {
        apply
            .getBody()
            .ifPresent(
                body ->
                    body.addAndGetStatement(
                            new IfStmt()
                                .setCondition(
                                    new MethodCallExpr(
                                            new StringLiteralExpr(getEnumName(enumConstant)),
                                            "equals")
                                        .addArgument(new NameExpr("value"))))
                        .setThenStmt(
                            new ReturnStmt(new NameExpr(bean.toString() + "." + enumConstant))));
      }
    }

    apply
        .getBody()
        .ifPresent(body -> body.addAndGetStatement(new ReturnStmt(new NullLiteralExpr())));

    expr.addArgument(function);
    return expr;
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(EnumXMLSerializer.class);
    cu.addImport(Function.class);

    NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();
    NodeList<Type> typeArguments = new NodeList<>();
    typeArguments.add(new ClassOrInterfaceType().setName(field.getBean().toString()));
    typeArguments.add(new ClassOrInterfaceType().setName("String"));

    ClassOrInterfaceType type = new ClassOrInterfaceType().setName("Function");
    type.setTypeArguments(typeArguments);

    ObjectCreationExpr function = new ObjectCreationExpr().setType(type);
    function.setAnonymousClassBody(anonymousClassBody);

    MethodDeclaration apply = new MethodDeclaration();
    apply.setModifiers(Modifier.Keyword.PUBLIC);
    apply.addAnnotation(Override.class);
    apply.setName("apply");
    apply.setType(new ClassOrInterfaceType().setName("String"));
    apply.addParameter(field.getBean().toString(), "value");

    anonymousClassBody.add(apply);

    for (Element enumConstant : MoreTypes.asTypeElement(bean).getEnclosedElements()) {
      if (enumConstant.getKind().equals(ElementKind.ENUM_CONSTANT)) {
        apply
            .getBody()
            .ifPresent(
                body ->
                    body.addAndGetStatement(
                            new IfStmt()
                                .setCondition(
                                    new MethodCallExpr(
                                            new NameExpr(bean.toString() + "." + enumConstant),
                                            "equals")
                                        .addArgument(new NameExpr("value"))))
                        .setThenStmt(
                            new ReturnStmt(new StringLiteralExpr(getEnumName(enumConstant)))));
      }
    }

    apply
        .getBody()
        .ifPresent(body -> body.addAndGetStatement(new ReturnStmt(new NullLiteralExpr())));
    return new MethodCallExpr(new NameExpr(EnumXMLSerializer.class.getSimpleName()), "getInstance")
        .addArgument(function);
  }

  private String getEnumName(Element enumConstant) {
    String enumName = enumConstant.toString();
    if (enumConstant.getAnnotation(XmlEnumValue.class) != null) {
      enumName = enumConstant.getAnnotation(XmlEnumValue.class).value();
    }
    return enumName;
  }

  @Override
  public String toString() {
    return "EnumBeanFieldDefinition{" + "bean=" + bean + '}';
  }
}
