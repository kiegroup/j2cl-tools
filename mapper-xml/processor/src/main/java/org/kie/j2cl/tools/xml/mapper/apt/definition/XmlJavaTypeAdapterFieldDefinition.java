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
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.google.auto.common.MoreTypes;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.function.Function;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.kie.j2cl.tools.xml.mapper.api.deser.XmlJavaAdapterDeserializer;
import org.kie.j2cl.tools.xml.mapper.api.ser.XmlJavaAdapterSerializer;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;

public class XmlJavaTypeAdapterFieldDefinition extends FieldDefinition {
  private final FieldDefinition fieldDefinition;
  private final XmlJavaTypeAdapter typeAdapter;
  private final TypeMirror marshal;
  private final TypeMirror umarshal;

  XmlJavaTypeAdapterFieldDefinition(TypeMirror type, GenerationContext context) {
    this(type, context, MoreTypes.asTypeElement(type).getAnnotation(XmlJavaTypeAdapter.class));
  }

  XmlJavaTypeAdapterFieldDefinition(
      TypeMirror type, GenerationContext context, XmlJavaTypeAdapter typeAdapter) {
    super(type, context);
    this.typeAdapter = typeAdapter;

    ExecutableElement decl = getUnmarshal();
    VariableElement parameterElement = decl.getParameters().get(0);
    fieldDefinition = propertyDefinitionFactory.getFieldDefinition(parameterElement.asType());

    marshal = parameterElement.asType();
    umarshal = decl.getReturnType();
  }

  private ExecutableElement getUnmarshal() {
    return ElementFilter.methodsIn(
            MoreTypes.asTypeElement(getAnnotation(typeAdapter)).getEnclosedElements())
        .stream()
        .filter(field -> field.getSimpleName().toString().equals("unmarshal"))
        .findFirst()
        .get();
  }

  private TypeMirror getAnnotation(XmlJavaTypeAdapter typeAdapter) {
    try {
      typeAdapter.value();
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
    return null;
  }

  @Override
  public Expression getFieldDeserializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(Function.class);
    cu.addImport(XmlJavaAdapterDeserializer.class);

    NodeList<Type> wrapperArguments = new NodeList<>();
    wrapperArguments.add(new ClassOrInterfaceType().setName(marshal.toString()));
    wrapperArguments.add(new ClassOrInterfaceType().setName(umarshal.toString()));

    ClassOrInterfaceType wrapper =
        new ClassOrInterfaceType().setName(XmlJavaAdapterDeserializer.class.getSimpleName());
    wrapper.setTypeArguments(wrapperArguments);

    ObjectCreationExpr newFunc =
        makeFunctionExpr(wrapperArguments, umarshal.toString(), marshal.toString(), "unmarshal");

    ObjectCreationExpr newWrapper = new ObjectCreationExpr().setType(wrapper);
    newWrapper.addArgument(fieldDefinition.getFieldDeserializer(field, cu)).addArgument(newFunc);

    return newWrapper;
  }

  @Override
  public Expression getFieldSerializer(PropertyDefinition field, CompilationUnit cu) {
    cu.addImport(Function.class);
    cu.addImport(XmlJavaAdapterSerializer.class);

    NodeList<Type> wrapperArguments = new NodeList<>();
    wrapperArguments.add(new ClassOrInterfaceType().setName(umarshal.toString()));
    wrapperArguments.add(new ClassOrInterfaceType().setName(marshal.toString()));

    ClassOrInterfaceType wrapper =
        new ClassOrInterfaceType().setName(XmlJavaAdapterSerializer.class.getSimpleName());
    wrapper.setTypeArguments(wrapperArguments);

    ObjectCreationExpr newFunc =
        makeFunctionExpr(wrapperArguments, marshal.toString(), umarshal.toString(), "marshal");

    ObjectCreationExpr newWrapper = new ObjectCreationExpr().setType(wrapper);
    newWrapper.addArgument(fieldDefinition.getFieldSerializer(field, cu)).addArgument(newFunc);

    return newWrapper;
  }

  private ObjectCreationExpr makeFunctionExpr(
      NodeList<Type> wrapperArguments, String from, String to, String operation) {
    NodeList<BodyDeclaration<?>> anonymousFuncBody = new NodeList<>();

    ClassOrInterfaceType func = new ClassOrInterfaceType().setName(Function.class.getSimpleName());
    func.setTypeArguments(wrapperArguments);

    ObjectCreationExpr newFunc = new ObjectCreationExpr().setType(func);
    newFunc.setAnonymousClassBody(anonymousFuncBody);

    MethodDeclaration method = new MethodDeclaration();
    method.setModifiers(Modifier.Keyword.PUBLIC);
    method.addAnnotation(Override.class);
    method.setName("apply");
    method.setType(new ClassOrInterfaceType().setName(from));
    method.addParameter(to, "value");
    anonymousFuncBody.add(method);

    BlockStmt rtrnBlock = new BlockStmt();
    BlockStmt exceptionBlock = new BlockStmt();
    exceptionBlock.addStatement(new ReturnStmt().setExpression(new NullLiteralExpr()));

    NodeList<CatchClause> catchClauses = new NodeList<>();
    catchClauses.add(
        new CatchClause()
            .setParameter(new Parameter().setType(Exception.class).setName("e"))
            .setBody(exceptionBlock));

    rtrnBlock.addStatement(
        new ReturnStmt(
            new MethodCallExpr(
                    new ObjectCreationExpr().setType(getAnnotation(typeAdapter).toString()),
                    operation)
                .addArgument("value")));

    method
        .getBody()
        .ifPresent(
            body ->
                body.addAndGetStatement(
                    new TryStmt().setTryBlock(rtrnBlock).setCatchClauses(catchClauses)));
    return newFunc;
  }
}
