/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.j2cl.transpiler.passes;

import com.google.j2cl.transpiler.ast.AbstractRewriter;
import com.google.j2cl.transpiler.ast.BinaryExpression;
import com.google.j2cl.transpiler.ast.CatchClause;
import com.google.j2cl.transpiler.ast.CompilationUnit;
import com.google.j2cl.transpiler.ast.Expression;
import com.google.j2cl.transpiler.ast.MethodCall;
import com.google.j2cl.transpiler.ast.Node;
import com.google.j2cl.transpiler.ast.RuntimeMethods;
import com.google.j2cl.transpiler.ast.ThrowStatement;

/**
 * Adds Java Throwable to JavaScript Error conversion.
 *
 * <p>This pass makes sure that exceptions work well across Java/JavaScript boundary. Instead of
 * throwing subclasses of Java Throwable class in generated throw statements, we basically throw the
 * backing JavaScript error object so the generated code looks like this:
 *
 * <pre>
 * <code>
 *   try {
 *     throw $Exceptions.toJs(new SomeJavaException);
 *   } catch(e) {
 *     e = $Exceptions.toJava(e);
 *     if (SomeJavaException.$isInstance(e)) {
 *       // catch block for SomeJavaException
 *     } else {
 *       throw $Exception.toJs(e);
 *     }
 *   }
 * </code>
 * </pre>
 *
 * <p>As the propagated thrown object is converted to real JavaScript error, it plays better with
 * the browser dev tools (doesn't work well with custom error objects) and callers from JavaScript
 * side.
 */
public class InsertExceptionConversions extends NormalizationPass {
  @Override
  public void applyTo(CompilationUnit compilationUnit) {
    compilationUnit.accept(
        new AbstractRewriter() {
          @Override
          public Node rewriteCatchClause(CatchClause catchClause) {
            if (catchClause.getBody().isNoop()) {
              return catchClause;
            }

            MethodCall toJavaCall =
                RuntimeMethods.createExceptionsMethodCall(
                    "toJava", catchClause.getExceptionVariable().createReference());

            Expression assignment =
                BinaryExpression.Builder.asAssignmentTo(catchClause.getExceptionVariable())
                    .setRightOperand(toJavaCall)
                    .build();

            catchClause
                .getBody()
                .getStatements()
                .add(0, assignment.makeStatement(catchClause.getBody().getSourcePosition()));

            return catchClause;
          }

          @Override
          public Node rewriteThrowStatement(ThrowStatement throwStatement) {

            MethodCall toJsCall =
                RuntimeMethods.createExceptionsMethodCall("toJs", throwStatement.getExpression());

            return ThrowStatement.Builder.from(throwStatement).setExpression(toJsCall).build();
          }
        });
  }

}
