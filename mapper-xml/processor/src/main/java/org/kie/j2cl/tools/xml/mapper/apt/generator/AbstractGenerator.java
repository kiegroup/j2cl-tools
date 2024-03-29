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
package org.kie.j2cl.tools.xml.mapper.apt.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.auto.common.MoreElements;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.processing.FilerException;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import org.kie.j2cl.tools.xml.mapper.apt.TypeUtils;
import org.kie.j2cl.tools.xml.mapper.apt.context.GenerationContext;
import org.kie.j2cl.tools.xml.mapper.apt.definition.BeanDefinition;
import org.kie.j2cl.tools.xml.mapper.apt.exception.GenerationException;
import org.kie.j2cl.tools.xml.mapper.apt.logger.TreeLogger;

public abstract class AbstractGenerator {

  protected final GenerationContext context;
  protected final TypeUtils typeUtils;
  protected final TreeLogger logger;
  protected CompilationUnit cu;
  protected ClassOrInterfaceDeclaration declaration;

  public AbstractGenerator(GenerationContext context, TreeLogger logger) {
    this.context = context;
    this.logger = logger;
    this.typeUtils = context.getTypeUtils();
  }

  public void generate(BeanDefinition type) {
    cu = new CompilationUnit();
    cu.setPackageDeclaration(context.getTypeUtils().getPackage(type.getBean()));
    declaration = cu.addClass(getMapperName(type.getElement()));

    // addGeneratedAnnotation(declaration);
    configureClassType(type);
    addTypeParam(type, declaration);
    getType(type);
    init(type);
    write(type.getElement());
  }

  private void addGeneratedAnnotation(ClassOrInterfaceDeclaration declaration) {
    NormalAnnotationExpr generated = new NormalAnnotationExpr();
    generated.setName("javax.annotation.processing.Generated");
    generated.addPair("value", new StringLiteralExpr(this.getClass().getCanonicalName()));
    declaration.addAnnotation(generated);
  }

  protected abstract String getMapperName(TypeElement type);

  protected abstract void configureClassType(BeanDefinition type);

  protected void addTypeParam(BeanDefinition type, ClassOrInterfaceDeclaration declaration) {}

  protected void getType(BeanDefinition type) {}

  protected abstract void init(BeanDefinition type);

  protected void write(TypeElement type) {
    logger.branch(TreeLogger.INFO, "Writing " + getMapperName(type));
    TypeMirror property = context.getTypeUtils().removeOuterWildCards(type.asType());
    if (!context.getTypeRegistry().containsDeserializer(property)) {
      try {
        build(MoreElements.getPackage(type) + "." + getMapperName(type), cu.toString());
      } catch (javax.annotation.processing.FilerException e1) {
        logger.log(TreeLogger.ERROR, e1.getMessage());
      } catch (IOException e1) {
        throw new GenerationException(e1);
      }
    }
  }

  private void build(String fileName, String source) throws IOException {
    JavaFileObject builderFile = context.getProcessingEnv().getFiler().createSourceFile(fileName);

    try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
      out.append(source);
    } catch (FilerException e) {
      throw new GenerationException(e);
    }
  }
}
