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
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.Inheritance;
import org.kie.j2cl.tools.xml.mapper.api.XMLDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.exception.XMLDeserializationException;
import org.kie.j2cl.tools.xml.mapper.api.stream.XMLReader;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public abstract class FieldDefinition extends Definition {

  protected FieldDefinition(TypeMirror property, GenerationContext context) {
    super(property, context);
  }

  protected Expression generateXMLDeserializerFactory(
      PropertyDefinition field, TypeMirror type, String typeArg, CompilationUnit cu) {
    return generateXMLDeserializerFactory(
        field, type, typeArg, cu, maybePolymorphicType(field, type));
  }

  protected Expression generateXMLDeserializerFactory(
      PropertyDefinition field,
      TypeMirror type,
      String typeArg,
      CompilationUnit cu,
      Pair<Class, Map<String, TypeMirror>> maybePolymorphicType) {
    TypeUtils typeUtils = context.getTypeUtils();

    cu.addImport(Function.class);
    cu.addImport(XMLReader.class);
    cu.addImport(XMLDeserializationException.class);
    NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();

    NodeList<Type> typeArguments = new NodeList<>();
    typeArguments.add(new ClassOrInterfaceType().setName("String"));
    typeArguments.add(
        new ClassOrInterfaceType()
            .setName(XMLDeserializer.class.getSimpleName())
            .setTypeArguments(new TypeParameter().setName(typeArg)));

    ClassOrInterfaceType iface = new ClassOrInterfaceType().setName(Function.class.getSimpleName());
    iface.setTypeArguments(typeArguments);

    ObjectCreationExpr func = new ObjectCreationExpr().setType(iface);
    func.setAnonymousClassBody(anonymousClassBody);

    MethodDeclaration method = new MethodDeclaration();
    method.setModifiers(Modifier.Keyword.PUBLIC);
    method.addAnnotation(Override.class);
    method.setName("apply");
    method.setType(new ClassOrInterfaceType().setName(XMLDeserializer.class.getSimpleName()));
    method.addParameter("String", "value");
    for (Map.Entry<String, TypeMirror> typeElement : maybePolymorphicType.value.entrySet()) {
      method
          .getBody()
          .ifPresent(
              body ->
                  body.addAndGetStatement(
                          new IfStmt()
                              .setCondition(
                                  new MethodCallExpr(
                                          new StringLiteralExpr(typeElement.getKey()), "equals")
                                      .addArgument(new NameExpr("value"))))
                      .setThenStmt(
                          new ReturnStmt(
                              new ObjectCreationExpr()
                                  .setType(
                                      new ClassOrInterfaceType()
                                          .setName(
                                              typeUtils.canonicalDeserializerName(
                                                  typeElement.getValue()))))));
    }
    anonymousClassBody.add(method);

    Statement expression;
    TypeMirror typeMirror = typeUtils.getTypeMirror(field).orElse(type);
    if (field.hasXmlJavaTypeAdapter()) {
      expression =
          new ReturnStmt(
              new XmlJavaTypeAdapterFieldDefinition(
                      typeMirror, context, field.getXmlJavaTypeAdapter())
                  .getFieldDeserializer(field, cu));

    } else if (MoreTypes.asTypeElement(typeMirror)
        .getModifiers()
        .contains(javax.lang.model.element.Modifier.ABSTRACT)) {
      if (context
          .getTypeUtils()
          .isSimpleType(context.getProcessingEnv().getTypeUtils().erasure(type))) {
        expression =
            new ReturnStmt(
                propertyDefinitionFactory.getFieldDefinition(type).getFieldDeserializer(field, cu));
      } else {
        ClassOrInterfaceType instanceBuilderType =
            new ClassOrInterfaceType().setName(XMLDeserializationException.class.getSimpleName());
        expression =
            new ThrowStmt()
                .setExpression(
                    new ObjectCreationExpr()
                        .setType(instanceBuilderType)
                        .addArgument(new NameExpr("\"Unknown property '\" + value + \"'\"")));
      }
    } else {
      expression =
          new ReturnStmt(
              propertyDefinitionFactory.getFieldDefinition(type).getFieldDeserializer(null, cu));
    }
    method.getBody().ifPresent(body -> body.addAndGetStatement(expression));
    return func;
  }

  public abstract Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu);

  protected Pair<Class, Map<String, TypeMirror>> maybePolymorphicType(
      PropertyDefinition field, TypeMirror type) {
    if (field != null && field.getProperty() != null) {
      XmlElements xmlElements = field.getProperty().getAnnotation(XmlElements.class);
      if (xmlElements != null) {
        return new Pair<>(
            XmlElements.class,
            TypeUtils.getXmlElements(context, field.getProperty(), XmlElements.class));
      }
      XmlElementRefs xmlElementRefs = field.getProperty().getAnnotation(XmlElementRefs.class);
      if (xmlElementRefs != null) {
        return new Pair<>(
            XmlElementRefs.class,
            TypeUtils.getXmlElements(context, field.getProperty(), XmlElementRefs.class));
      }
      XmlSeeAlso xmlSeeAlso = MoreTypes.asTypeElement(type).getAnnotation(XmlSeeAlso.class);
      if (xmlSeeAlso != null) {
        Map<String, TypeMirror> result = new HashMap<>();
        for (TypeElement typeElement : getXmlSeeAlso(xmlSeeAlso)) {
          result.put(
              context.getBeanDefinition(typeElement.asType()).getXmlRootElement(),
              typeElement.asType());
        }

        return new Pair<>(XmlSeeAlso.class, result);
      }
    }
    return new Pair<>(Class.class, Collections.emptyMap());
  }

  private TypeElement[] getXmlSeeAlso(XmlSeeAlso xmlSeeAlso) {
    try {
      xmlSeeAlso.value();
    } catch (MirroredTypesException e) {
      TypeElement[] result = new TypeElement[e.getTypeMirrors().size()];
      for (int i = 0; i < e.getTypeMirrors().size(); i++) {
        result[i] = MoreTypes.asTypeElement(e.getTypeMirrors().get(i));
      }
      return result;
    }
    return new TypeElement[0];
  }

  protected Expression generateXMLSerializerFactory(
      PropertyDefinition field, TypeMirror type, String typeArg, CompilationUnit cu) {
    TypeUtils typeUtils = context.getTypeUtils();
    NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();

    NodeList<Type> typeArguments = new NodeList<>();
    typeArguments.add(new ClassOrInterfaceType().setName("Class"));
    typeArguments.add(
        new ClassOrInterfaceType()
            .setName("XMLSerializer")
            .setTypeArguments(new TypeParameter().setName(typeArg)));

    ClassOrInterfaceType iface = new ClassOrInterfaceType().setName("Function");
    iface.setTypeArguments(typeArguments);

    ObjectCreationExpr func = new ObjectCreationExpr().setType(iface);
    func.setAnonymousClassBody(anonymousClassBody);

    MethodDeclaration method = new MethodDeclaration();
    method.setModifiers(Modifier.Keyword.PUBLIC);
    method.addAnnotation(Override.class);
    method.setName("apply");
    method.setType(new ClassOrInterfaceType().setName("XMLSerializer"));
    method.addParameter("Class", "value");

    maybeAddXMLSerializers(field, type, typeUtils, method, cu);
    anonymousClassBody.add(method);

    Statement defaultReturn;
    TypeMirror typeMirror = typeUtils.getTypeMirror(field).orElse(type);
    if (field.hasXmlJavaTypeAdapter()) {
      defaultReturn =
          new ReturnStmt(
              new XmlJavaTypeAdapterFieldDefinition(
                      typeMirror, context, field.getXmlJavaTypeAdapter())
                  .getFieldSerializer(field, cu));

    } else if (MoreTypes.asTypeElement(typeMirror)
        .getModifiers()
        .contains(javax.lang.model.element.Modifier.ABSTRACT)) {
      if (context
          .getTypeUtils()
          .isSimpleType(context.getProcessingEnv().getTypeUtils().erasure(type))) {
        defaultReturn =
            new ReturnStmt(
                propertyDefinitionFactory.getFieldDefinition(type).getFieldSerializer(field, cu));
      } else {
        ClassOrInterfaceType instanceBuilderType =
            new ClassOrInterfaceType().setName(Error.class.getSimpleName());
        defaultReturn =
            new ThrowStmt().setExpression(new ObjectCreationExpr().setType(instanceBuilderType));
      }
    } else {
      defaultReturn =
          new ReturnStmt(
              propertyDefinitionFactory
                  .getFieldDefinition(typeMirror)
                  .getFieldSerializer(null, cu));
    }

    method.getBody().ifPresent(body -> body.addAndGetStatement(defaultReturn));

    return func;
  }

  private void maybeAddXMLSerializers(
      PropertyDefinition field,
      TypeMirror type,
      TypeUtils typeUtils,
      MethodDeclaration method,
      CompilationUnit cu) {
    cu.addImport(Function.class);
    cu.addImport(Inheritance.class);
    Pair<Class, Map<String, TypeMirror>> pair = maybePolymorphicType(field, type);
    String inheritance =
        pair.key.equals(XmlElementRefs.class) ? "Inheritance.TAG" : "Inheritance.XSI";
    for (Map.Entry<String, TypeMirror> typeElement : pair.value.entrySet()) {
      MethodCallExpr methodCallExpr =
          new MethodCallExpr(
                  new ObjectCreationExpr()
                      .setType(
                          new ClassOrInterfaceType()
                              .setName(typeUtils.canonicalSerializerName(typeElement.getValue()))),
                  "setType")
              .addArgument(new StringLiteralExpr(typeElement.getKey()))
              .addArgument(new NameExpr(inheritance));
      // TODO
      if (inheritance.equals("Inheritance.XSI")) {
        methodCallExpr =
            new MethodCallExpr(methodCallExpr, "addNamespace")
                .addArgument(new StringLiteralExpr("xsi"))
                .addArgument(new StringLiteralExpr("http://www.w3.org/2001/XMLSchema-instance"));
      }

      ReturnStmt returnStmt = new ReturnStmt(methodCallExpr);
      method
          .getBody()
          .ifPresent(
              body ->
                  body.addAndGetStatement(
                          new IfStmt()
                              .setCondition(
                                  new MethodCallExpr(
                                          new FieldAccessExpr(
                                              new NameExpr(typeElement.getValue().toString()),
                                              "class"),
                                          "equals")
                                      .addArgument(new NameExpr("value"))))
                      .setThenStmt(returnStmt));
    }
  }

  public abstract Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu);
}
