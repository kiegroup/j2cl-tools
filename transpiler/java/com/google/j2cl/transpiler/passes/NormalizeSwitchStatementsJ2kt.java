/*
 * Copyright 2021 Google Inc.
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

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.google.j2cl.common.SourcePosition;
import com.google.j2cl.transpiler.ast.AbstractRewriter;
import com.google.j2cl.transpiler.ast.Block;
import com.google.j2cl.transpiler.ast.BreakStatement;
import com.google.j2cl.transpiler.ast.CompilationUnit;
import com.google.j2cl.transpiler.ast.ContinueStatement;
import com.google.j2cl.transpiler.ast.Expression;
import com.google.j2cl.transpiler.ast.IfStatement;
import com.google.j2cl.transpiler.ast.Label;
import com.google.j2cl.transpiler.ast.LabeledStatement;
import com.google.j2cl.transpiler.ast.Node;
import com.google.j2cl.transpiler.ast.NumberLiteral;
import com.google.j2cl.transpiler.ast.PrimitiveTypeDescriptor;
import com.google.j2cl.transpiler.ast.ReturnStatement;
import com.google.j2cl.transpiler.ast.Statement;
import com.google.j2cl.transpiler.ast.SwitchCase;
import com.google.j2cl.transpiler.ast.SwitchStatement;
import com.google.j2cl.transpiler.ast.ThrowStatement;
import com.google.j2cl.transpiler.ast.TypeDescriptor;
import java.util.List;

/**
 * Normalizes switch statement for Kotlin, into cascading labeled blocks.
 *
 * <p>Before:
 *
 * <pre>{@code
 * SWITCH: switch (x) {
 *   case 1:
 *     runCase1();
 *   case 2:
 *     runCase2();
 *     break SWITCH;
 *   default:
 *     runDefault();
 * }
 * }</pre>
 *
 * <p>After:
 *
 * <pre>{@code
 * SWITCH: {
 *   CASE_3: {
 *     CASE_2: {
 *       CASE_1: {
 *         switch (x) {
 *           case 1: break CASE_1;
 *           case 2: break CASE_2;
 *           default: break CASE_3:
 *         }
 *         break SWITCH;
 *       }
 *       runCase1();
 *     }
 *     runCase2();
 *     break SWITCH;
 *   }
 *   runDefault();
 * }
 * }</pre>
 */
public class NormalizeSwitchStatementsJ2kt extends NormalizationPass {
  /** Case/label pair. */
  @AutoValue
  abstract static class SwitchCaseWithLabel {
    abstract SwitchCase getSwitchCase();

    abstract Label getLabel();
  }

  @Override
  public void applyTo(CompilationUnit compilationUnit) {
    // Normalize switch case types.
    compilationUnit.accept(
        new AbstractRewriter() {
          @Override
          public Node rewriteSwitchStatement(SwitchStatement switchStatement) {
            return ensureExhaustive(normalizeSwitchCaseTypes(switchStatement));
          }
        });

    // Convert to cascading labeled blocks.
    compilationUnit.accept(
        new AbstractRewriter() {
          @Override
          public Node rewriteSwitchStatement(SwitchStatement switchStatement) {
            if (canConvertDirectlyToWhen(switchStatement)) {
              return switchStatement;
            }

            List<SwitchCaseWithLabel> switchCasesAndLabels =
                createLabelsForSwitchCases(switchStatement.getCases());

            Label switchLabel = ((LabeledStatement) getParent()).getLabel();
            Statement dispatchStatement =
                createDispatchStatement(
                    switchLabel,
                    switchStatement.getSwitchExpression(),
                    switchCasesAndLabels,
                    switchStatement.getSourcePosition());

            return createNestedCaseHandlingBlocks(
                dispatchStatement, switchCasesAndLabels, switchStatement.getSourcePosition());
          }
        });
  }

  /** Creates labels for each case, and returns case/label pairs. */
  private static List<SwitchCaseWithLabel> createLabelsForSwitchCases(
      List<SwitchCase> switchCases) {
    return switchCases.stream()
        .map(
            switchCase ->
                new AutoValue_NormalizeSwitchStatementsJ2kt_SwitchCaseWithLabel(
                    switchCase, Label.newBuilder().setName("CASE").build()))
        .collect(toImmutableList());
  }

  /**
   * Creates the logic to dispatch to target switch case.
   *
   * <pre>{@code
   * {
   *   switch (foo) {
   *     CASE_1: break LABEL_1;
   *     CASE_2: break LABEL_2;
   *     CASE_3: break LABEL_3;
   *   }
   *   break SWITCH_LABEL;
   * }
   * }</pre>
   */
  private static Statement createDispatchStatement(
      Label switchLabel,
      Expression switchExpression,
      List<SwitchCaseWithLabel> switchCasesWithLabels,
      SourcePosition sourcePosition) {
    ImmutableList<SwitchCase> cases =
        switchCasesWithLabels.stream()
            .map(
                switchCaseWithLabel ->
                    createCaseWithBreak(
                        switchCaseWithLabel.getSwitchCase(),
                        switchCaseWithLabel.getLabel(),
                        sourcePosition))
            .collect(toImmutableList());

    // Move `default` case at the end. Note that since this is just the dispatching switch, it does
    // not have fallthroughs and can be arbitrarily reordered.
    cases =
        Streams.concat(
                cases.stream().filter(it -> it.getCaseExpression() != null),
                cases.stream().filter(it -> it.getCaseExpression() == null))
            .collect(toImmutableList());

    Statement rewrittenSwitchStatement =
        SwitchStatement.newBuilder()
            .setSourcePosition(sourcePosition)
            .setSwitchExpression(switchExpression)
            .setCases(cases)
            .build();

    return Block.newBuilder()
        .addStatement(rewrittenSwitchStatement)
        .addStatement(
            BreakStatement.newBuilder()
                .setSourcePosition(sourcePosition)
                .setLabelReference(switchLabel.createReference())
                .build())
        .build();
  }

  /**
   * Returns {@code switchCase} where statements are replaced with a break statement with {@code
   * label}.
   */
  private static SwitchCase createCaseWithBreak(
      SwitchCase switchCase, Label label, SourcePosition sourcePosition) {
    return SwitchCase.Builder.from(switchCase)
        .setStatements(
            BreakStatement.newBuilder()
                .setLabelReference(label.createReference())
                .setSourcePosition(sourcePosition)
                .build())
        .build();
  }

  /**
   * Wraps {@code dispatchStatement} with nested labeled statements for each case/label pair.
   *
   * <pre>{@code
   * {
   *   LABEL_3: {
   *     LABEL_2: {
   *       LABEL_1: {
   *         // Dispatch statement
   *       }
   *       // Case 1 statements
   *     }
   *     // Case 2 statements
   *   }
   *   // Case 3 statements
   * }
   * }</pre>
   */
  private static Statement createNestedCaseHandlingBlocks(
      Statement dispatchStatement,
      List<SwitchCaseWithLabel> switchCaseWithLabels,
      SourcePosition sourcePosition) {
    for (SwitchCaseWithLabel switchCaseWithLabel : switchCaseWithLabels) {
      dispatchStatement =
          Block.newBuilder()
              .addStatement(
                  LabeledStatement.newBuilder()
                      .setLabel(switchCaseWithLabel.getLabel())
                      .setStatement(dispatchStatement)
                      .setSourcePosition(sourcePosition)
                      .build())
              .addStatements(switchCaseWithLabel.getSwitchCase().getStatements())
              .build();
    }

    return dispatchStatement;
  }

  /** Convert switch case expressions to be of the same type as switch expression. */
  private static SwitchStatement normalizeSwitchCaseTypes(SwitchStatement switchStatement) {
    TypeDescriptor targetTypeDescriptor = switchStatement.getSwitchExpression().getTypeDescriptor();
    return SwitchStatement.Builder.from(switchStatement)
        .setCases(
            switchStatement.getCases().stream()
                .map(
                    switchCase -> convertSwitchCaseExpressionType(switchCase, targetTypeDescriptor))
                .collect(toImmutableList()))
        .build();
  }

  private static SwitchCase convertSwitchCaseExpressionType(
      SwitchCase switchCase, TypeDescriptor targetTypeDescriptor) {
    Expression caseExpression = switchCase.getCaseExpression();
    if (caseExpression == null) {
      return switchCase;
    }

    if (!(caseExpression instanceof NumberLiteral)) {
      return switchCase;
    }

    NumberLiteral literal = (NumberLiteral) caseExpression;
    return SwitchCase.Builder.from(switchCase)
        .setCaseExpression(
            new NumberLiteral((PrimitiveTypeDescriptor) targetTypeDescriptor, literal.getValue()))
        .build();
  }

  /**
   * For a switch to be directly expressible as a when in Kotlin two conditions have to be met: the
   * default case needs to be the last one, and there should not be any fallthroughs except for
   * empty cases.
   */
  private static boolean canConvertDirectlyToWhen(SwitchStatement switchStatement) {
    List<SwitchCase> cases = switchStatement.getCases();
    List<SwitchCase> casesExceptLast = cases.isEmpty() ? cases : cases.subList(0, cases.size() - 1);
    return casesExceptLast.stream()
        .allMatch(
            switchCase ->
                switchCase.getCaseExpression() != null
                    && (switchCase.getStatements().isEmpty()
                        || breaksOutOfSwitchStatement(switchCase.getStatements())));
  }

  /**
   * A conservative approximation of when a statement is guaranteed to exit the switch clause
   * without falling through the next case clause. In particular labeled statements are completely
   * skipped to avoid reasoning about local jumps in control flow.
   */
  private static boolean breaksOutOfSwitchStatement(Statement statement) {
    if (statement instanceof ReturnStatement) {
      return true;
    }

    if (statement instanceof ThrowStatement) {
      return true;
    }

    if (statement instanceof BreakStatement || statement instanceof ContinueStatement) {
      // Since we are not entering labeled statements, loop statements and other switch statements,
      // these break and continue statement are guaranteed to target outside switch statement.
      return true;
    }

    if (statement instanceof Block) {
      Block block = (Block) statement;
      return breaksOutOfSwitchStatement(block.getStatements());
    }

    if (statement instanceof IfStatement) {
      IfStatement ifStatement = (IfStatement) statement;
      Statement elseStatement = ifStatement.getElseStatement();
      return breaksOutOfSwitchStatement(ifStatement.getThenStatement())
          && elseStatement != null
          && breaksOutOfSwitchStatement(elseStatement);
    }

    return false;
  }

  private static boolean breaksOutOfSwitchStatement(List<Statement> statements) {
    Statement lastStatement = Iterables.getLast(statements, null);
    return lastStatement != null && breaksOutOfSwitchStatement(lastStatement);
  }

  private static SwitchStatement ensureExhaustive(SwitchStatement switchStatement) {
    if (switchStatement.getCases().stream()
        .anyMatch(switchCase -> switchCase.getCaseExpression() == null)) {
      return switchStatement;
    }

    return SwitchStatement.Builder.from(switchStatement)
        .addCases(SwitchCase.newBuilder().build())
        .build();
  }
}
