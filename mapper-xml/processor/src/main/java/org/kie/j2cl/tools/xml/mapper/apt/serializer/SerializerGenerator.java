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
package org.kie.j2cl.tools.xml.mapper.apt.serializer;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import jakarta.xml.bind.annotation.XmlAccessType;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.kie.j2cl.tools.xml.mapper.api.PropertyType;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializationContext;
import org.kie.j2cl.tools.xml.mapper.api.XMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.XmlElementWrapperSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.bean.AbstractBeanXMLSerializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.bean.BeanPropertySerializer;
import org.kie.j2cl.tools.xml.mapper.api.utils.Pair;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.xml.mapper.apt.definition.PropertyDefinition;
import org.kie.j2cl.tools.xml.mapper.apt.generator.AbstractGenerator;
import org.kie.j2cl.tools.xml.mapper.apt.logger.TreeLogger;

public class SerializerGenerator extends AbstractGenerator {

  public static final String STRING = "String";
  public static final String RESULT = "result";
  private ConstructorDeclaration constructor;

  public SerializerGenerator(GenerationContext context, TreeLogger logger) {
    super(context, logger.branch(TreeLogger.INFO, "Serializers generation started"));
  }

  @Override
  protected String getMapperName(TypeElement type) {
    return context.getTypeUtils().serializerName(type.asType());
  }

  @Override
  protected void configureClassType(BeanDefinition type) {
    cu.addImport(XMLSerializationContext.class);
    cu.addImport(XMLSerializer.class);
    cu.addImport(AbstractBeanXMLSerializer.class);
    cu.addImport(Pair.class);
    cu.addImport(List.class);
    cu.addImport(BeanPropertySerializer.class);
    cu.addImport(XMLSerializer.class);
    cu.addImport(type.getQualifiedName());

    declaration
        .getExtendedTypes()
        .add(
            new ClassOrInterfaceType()
                .setName(AbstractBeanXMLSerializer.class.getSimpleName())
                .setTypeArguments(new ClassOrInterfaceType().setName(type.getSimpleName())));
    constructor = declaration.addConstructor(Modifier.Keyword.PUBLIC);
  }

  @Override
  protected void getType(BeanDefinition type) {
    getSerializedType(type);
    getXmlRootElement(type);
    getXmlValuePropertyName(type);
    getXmlNs(type);
    getSchemaLocation(type);
    getDefaultNamespace(type);
    getTargetNamespace(type);
    getXsiType(type);
  }

  private void getSerializedType(BeanDefinition type) {
    declaration
        .addMethod("getSerializedType", Modifier.Keyword.PUBLIC)
        .addAnnotation(Override.class)
        .setType(Class.class)
        .getBody()
        .ifPresent(
            body ->
                body.addStatement(
                    new ReturnStmt(
                        new FieldAccessExpr(new NameExpr(type.getSimpleName()), "class"))));
  }

  private void getXmlRootElement(BeanDefinition type) {
    if (type.getXmlRootElement() != null) {
      declaration
          .addMethod("getXmlRootElement", Modifier.Keyword.PROTECTED)
          .addAnnotation(Override.class)
          .setType(String.class)
          .getBody()
          .ifPresent(
              body ->
                  body.addStatement(
                      new ReturnStmt(new StringLiteralExpr(type.getXmlRootElement()))));
    }
  }

  private void getXmlValuePropertyName(BeanDefinition type) {
    type.getFields().stream()
        .filter(p -> p.isXmlValue() && !(p.isCData() && p.getCData().value()))
        .forEach(
            xmlvalue -> {
              declaration
                  .addMethod("getXmlValuePropertyName", Modifier.Keyword.PROTECTED)
                  .addAnnotation(Override.class)
                  .setType(String.class)
                  .getBody()
                  .ifPresent(
                      body ->
                          body.addStatement(
                              new ReturnStmt(new StringLiteralExpr(xmlvalue.getPropertyName()))));
            });
  }

  private void getXmlNs(BeanDefinition beanDefinition) {
    if (!beanDefinition.getXmlNs().isEmpty()) {
      beanDefinition
          .getXmlNs()
          .forEach(
              pair ->
                  constructor
                      .getBody()
                      .addStatement(
                          new MethodCallExpr(new NameExpr("namespaces"), "put")
                              .addArgument(
                                  pair.key != null
                                      ? new StringLiteralExpr(pair.key)
                                      : new StringLiteralExpr(""))
                              .addArgument(new StringLiteralExpr(pair.value))));
    }
  }

  private void getSchemaLocation(BeanDefinition type) {
    if (type.getSchemaLocation() != null) {
      declaration
          .addMethod("getSchemaLocation", Modifier.Keyword.PUBLIC)
          .addAnnotation(Override.class)
          .setType(String.class)
          .getBody()
          .ifPresent(
              body ->
                  body.addStatement(
                      new ReturnStmt(new StringLiteralExpr(type.getSchemaLocation()))));
    }
  }

  private void getDefaultNamespace(BeanDefinition type) {
    String namespace = type.getNamespace();
    if (namespace != null) {
      declaration
          .addMethod("getNamespace", Modifier.Keyword.PUBLIC)
          .addAnnotation(Override.class)
          .setType(String.class)
          .getBody()
          .ifPresent(body -> body.addStatement(new ReturnStmt(new StringLiteralExpr(namespace))));
    }
  }

  private void getTargetNamespace(BeanDefinition type) {
    Pair<String, String> targetNamespace = type.getTargetNamespace();
    if (targetNamespace != null) {
      cu.addImport(Pair.class);
      ClassOrInterfaceType pair =
          new ClassOrInterfaceType()
              .setName("Pair")
              .setTypeArguments(
                  new ClassOrInterfaceType().setName(STRING),
                  new ClassOrInterfaceType().setName(STRING));
      declaration
          .addMethod("getTargetNamespace", Modifier.Keyword.PROTECTED)
          .addAnnotation(Override.class)
          .setType(pair)
          .getBody()
          .ifPresent(
              body ->
                  body.addStatement(
                      new ReturnStmt(
                          new ObjectCreationExpr()
                              .setType(pair)
                              .addArgument(new StringLiteralExpr(targetNamespace.key))
                              .addArgument(new StringLiteralExpr(targetNamespace.value)))));
    }
  }

  private void getXsiType(BeanDefinition type) {
    if (type.getXsiType() != null) {
      for (String s : type.getXsiType()) {
        constructor
            .getBody()
            .addStatement(
                new MethodCallExpr(new NameExpr("xsiType"), "add")
                    .addArgument(new StringLiteralExpr(s)));
      }
    }
  }

  @Override
  protected void init(BeanDefinition beanDefinition) {
    logger.log(
        TreeLogger.INFO,
        "Generating " + context.getTypeUtils().serializerName(beanDefinition.getBean()));
    MethodDeclaration initSerializers =
        declaration.addMethod("initSerializers", Modifier.Keyword.PROTECTED);
    initSerializers
        .addAnnotation(Override.class)
        .setType(BeanPropertySerializer[].class)
        .getBody()
        .ifPresent(body -> processInitSerializersMethodBody(body, beanDefinition));
  }

  private void processInitSerializersMethodBody(BlockStmt body, BeanDefinition beanDefinition) {

    List<PropertyDefinition> fields =
        beanDefinition.getFields().stream().collect(Collectors.toList());
    addBeanPropertySerializerDeclaration(body, fields);

    for (int i = 0; i < fields.size(); i++) {
      addBeanPropertySerializer(body, beanDefinition, fields.get(i), i);
    }

    body.addStatement(new ReturnStmt(new NameExpr(RESULT)));
  }

  private void addBeanPropertySerializerDeclaration(
      BlockStmt body, List<PropertyDefinition> fields) {
    VariableDeclarator result = new VariableDeclarator();
    result.setType("BeanPropertySerializer[]");
    result.setName(RESULT);
    result.setInitializer("new BeanPropertySerializer[" + fields.size() + "]");
    ExpressionStmt expressionStmt = new ExpressionStmt();
    VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();

    expressionStmt.setExpression(variableDeclarationExpr);
    variableDeclarationExpr.getVariables().add(result);
    body.addStatement(variableDeclarationExpr);
  }

  private void addBeanPropertySerializer(
      BlockStmt body, BeanDefinition beanDefinition, PropertyDefinition variableElement, int i) {
    ObjectCreationExpr beanProperty = new ObjectCreationExpr();
    ClassOrInterfaceType beanType =
        new ClassOrInterfaceType().setName(BeanPropertySerializer.class.getSimpleName());

    beanProperty.setType(beanType);
    beanProperty.addArgument(new StringLiteralExpr(variableElement.getPropertyName()));
    if (variableElement.isCData()) {
      String value =
          PropertyType.class.getCanonicalName()
              + "."
              + ((variableElement.getCData().value() && !variableElement.isXmlValue())
                  ? "CDATA"
                  : "CDATA_INLINE");
      beanProperty.addArgument(new NameExpr(value));
    }
    setTypeParams(beanDefinition, variableElement, beanType);

    body.addStatement(
        new AssignExpr()
            .setTarget(new ArrayAccessExpr(new NameExpr(RESULT), new IntegerLiteralExpr(i)))
            .setValue(beanProperty));

    addMethods(beanProperty, beanDefinition, variableElement);
  }

  private void setTypeParams(
      BeanDefinition beanDefinition,
      PropertyDefinition variableElement,
      ClassOrInterfaceType beanType) {
    NodeList<Type> typeArguments = new NodeList<>();
    typeArguments.add(new ClassOrInterfaceType().setName(beanDefinition.getSimpleName()));

    String fieldType;
    if (variableElement.getBean().getKind().isPrimitive()) {
      fieldType = TypeUtils.wrapperType(variableElement.getBean());
    } else if (variableElement.getBean().getKind().equals(TypeKind.ARRAY)) {
      ArrayType arrayType = (ArrayType) variableElement.getBean();
      fieldType = arrayType.toString();
    } else {
      fieldType = typeUtils.toTypeElement(variableElement.getBean()).toString();
    }

    ClassOrInterfaceType interfaceType = new ClassOrInterfaceType();
    interfaceType.setName(fieldType);

    addTypeArguments(variableElement.getBean(), interfaceType);
    typeArguments.add(interfaceType);
    beanType.setTypeArguments(typeArguments);
  }

  private void addTypeArguments(TypeMirror type, ClassOrInterfaceType interfaceType) {
    if (type instanceof DeclaredType) {
      if (!((DeclaredType) type).getTypeArguments().isEmpty()) {
        NodeList<Type> types = new NodeList<>();
        ((DeclaredType) type)
            .getTypeArguments()
            .forEach(param -> types.add(new ClassOrInterfaceType().setName(param.toString())));
        interfaceType.setTypeArguments(types);
      }
    }
  }

  private void addMethods(
      ObjectCreationExpr beanProperty,
      BeanDefinition beanDefinition,
      PropertyDefinition propertyDefinition) {
    NodeList<BodyDeclaration<?>> anonymousClassBody = new NodeList<>();
    beanProperty.setAnonymousClassBody(anonymousClassBody);

    newSerializer(anonymousClassBody, propertyDefinition);
    getValue(anonymousClassBody, beanDefinition, propertyDefinition);
    isAttribute(anonymousClassBody, propertyDefinition);
    getNamespace(anonymousClassBody, propertyDefinition);
    getPrefix(anonymousClassBody, beanDefinition, propertyDefinition);
  }

  private void newSerializer(
      NodeList<BodyDeclaration<?>> anonymousClassBody, PropertyDefinition field) {
    MethodDeclaration method = new MethodDeclaration();
    method.setModifiers(Modifier.Keyword.PROTECTED);
    method.addAnnotation(Override.class);
    method.setName("newSerializer");
    method.addParameter(Class.class.getSimpleName(), "value");

    method.setType(new ClassOrInterfaceType().setName("XMLSerializer<?>"));

    method
        .getBody()
        .ifPresent(
            body ->
                body.addAndGetStatement(
                    new ReturnStmt().setExpression(createFieldSerializerExpr(field))));
    anonymousClassBody.add(method);
  }

  private Expression createFieldSerializerExpr(PropertyDefinition field) {
    Expression expr = field.getFieldSerializer(cu);
    if (field.isWrapped()) {
      ClassOrInterfaceType wrapper =
          new ClassOrInterfaceType().setName(XmlElementWrapperSerializer.class.getCanonicalName());
      ObjectCreationExpr beanProperty = new ObjectCreationExpr();
      beanProperty.setType(wrapper);
      Pair<String, String> wrapped = field.getWrapped();
      expr = beanProperty.addArgument(expr).addArgument(new StringLiteralExpr(wrapped.key));
      if (wrapped.value != null) {
        expr =
            new MethodCallExpr(expr, "setDefaultNamespace")
                .addArgument(new StringLiteralExpr(wrapped.value));
      }
    }
    return expr;
  }

  private void getValue(
      NodeList<BodyDeclaration<?>> anonymousClassBody,
      BeanDefinition bean,
      PropertyDefinition field) {
    MethodDeclaration method = new MethodDeclaration();
    method.setModifiers(Modifier.Keyword.PUBLIC);
    method.addAnnotation(Override.class);
    method.setName("getValue");
    method.addParameter(new ClassOrInterfaceType().setName(bean.getSimpleName()), "bean");
    method.addParameter(XMLSerializationContext.class.getSimpleName(), "ctx");

    ClassOrInterfaceType interfaceType =
        new ClassOrInterfaceType().setName(TypeUtils.wrapperType(field.getBean()));

    method.setType(interfaceType);
    method
        .getBody()
        .ifPresent(body -> body.addAndGetStatement(new ReturnStmt(getFieldAccessor(bean, field))));
    anonymousClassBody.add(method);
  }

  private Expression getFieldAccessor(BeanDefinition beanDefinition, PropertyDefinition field) {
    boolean hasGetter = typeUtils.hasGetter(field.getProperty());

    if (beanDefinition.getAccessorType().equals(XmlAccessType.FIELD) || !hasGetter) {
      return new FieldAccessExpr(
          new NameExpr("bean"), field.getProperty().getSimpleName().toString());
    }
    return new MethodCallExpr(
        new NameExpr("bean"), typeUtils.getGetter(field.getProperty()).getSimpleName().toString());
  }

  private void isAttribute(
      NodeList<BodyDeclaration<?>> anonymousClassBody, PropertyDefinition propertyDefinition) {
    if (propertyDefinition.isAttribute()) {
      MethodDeclaration method = new MethodDeclaration();
      method.setModifiers(Modifier.Keyword.PROTECTED);
      method.addAnnotation(Override.class);
      method.setName("isAttribute");
      method.setType(new ClassOrInterfaceType().setName("boolean"));

      method
          .getBody()
          .ifPresent(
              body ->
                  body.addAndGetStatement(
                      new ReturnStmt().setExpression(new BooleanLiteralExpr(true))));
      anonymousClassBody.add(method);
    }
  }

  private void getNamespace(
      NodeList<BodyDeclaration<?>> anonymousClassBody, PropertyDefinition field) {
    if (field.getNamespace() != null) {
      MethodDeclaration method = new MethodDeclaration();
      method.setModifiers(Modifier.Keyword.PUBLIC);
      method.addAnnotation(Override.class);
      method.setName("getNamespace");
      method.setType(new ClassOrInterfaceType().setName(STRING));

      method
          .getBody()
          .ifPresent(
              body ->
                  body.addAndGetStatement(
                      new ReturnStmt().setExpression(new StringLiteralExpr(field.getNamespace()))));
      anonymousClassBody.add(method);
    }
  }

  private void getPrefix(
      NodeList<BodyDeclaration<?>> anonymousClassBody,
      BeanDefinition beanDefinition,
      PropertyDefinition propertyDefinition) {
    String prefix = null;
    if (propertyDefinition.getNamespace() != null) {
      for (Pair<String, String> xmlN : beanDefinition.getXmlNs()) {
        if (xmlN.value.equals(propertyDefinition.getNamespace()) && xmlN.key != null) {
          prefix = xmlN.key;
          break;
        }
      }
      if (prefix == null) {
        if (beanDefinition.getTargetNamespace() != null
            && beanDefinition
                .getTargetNamespace()
                .value
                .equals(propertyDefinition.getNamespace())) {
          prefix = beanDefinition.getTargetNamespace().key;
        }
      }
    }

    if (prefix != null) {
      MethodDeclaration method = new MethodDeclaration();
      method.setModifiers(Modifier.Keyword.PROTECTED);
      method.addAnnotation(Override.class);
      method.setName("getPrefix");
      method.setType(new ClassOrInterfaceType().setName(STRING));

      String finalPrefix = prefix;
      method
          .getBody()
          .ifPresent(
              body ->
                  body.addAndGetStatement(
                      new ReturnStmt().setExpression(new StringLiteralExpr(finalPrefix))));
      anonymousClassBody.add(method);
    }
  }
}
