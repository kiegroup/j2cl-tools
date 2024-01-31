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
package com.google.j2cl.transpiler.backend.common;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.j2cl.common.FilePosition;
import com.google.j2cl.common.SourcePosition;
import com.google.j2cl.transpiler.ast.MemberDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;

/** Builds source and tracks line numbers using a StringBuilder. */
public class SourceBuilder {

  // Simple platform dependent new line helps us to not think about potential implications in
  // J2clMinifier/RTA and other potential mistakes in the tooling. Since this is generated code we
  // can choose to have a platform specific new line and most (if not all) development tools will
  // handle that if somebody cares to take a look our generated code.
  private static final char LINE_SEPARATOR_CHAR = '\n';
  private static final String LINE_SEPARATOR = String.valueOf(LINE_SEPARATOR_CHAR);
  private static final String INDENT = " ";

  private final StringBuilder sb = new StringBuilder();
  private int currentLine = 0;
  private int currentColumn = 0;
  private int currentIndentation = 0;
  private final SortedMap<SourcePosition, SourcePosition> javaSourceInfoByOutputSourceInfo =
      new TreeMap<>();
  private final Map<MemberDescriptor, SourcePosition> outputSourceInfoByMember = new HashMap<>();
  private boolean finished = false;

  public void emitWithMapping(SourcePosition javaSourcePosition, Runnable codeEmitter) {
    checkNotNull(javaSourcePosition);

    SourcePosition outputSourcePosition = emit(codeEmitter);

    if (outputSourcePosition == SourcePosition.NONE || javaSourcePosition == SourcePosition.NONE) {
      // Do not record empty mappings.
      return;
    }
    javaSourceInfoByOutputSourceInfo.put(outputSourcePosition, javaSourcePosition);
  }

  public void emitWithMemberMapping(MemberDescriptor memberDescriptor, Runnable codeEmitter) {
    checkState(
        !outputSourceInfoByMember.containsKey(memberDescriptor),
        "Output source info already exists for this member %s",
        memberDescriptor);

    SourcePosition outputSourcePosition = emit(codeEmitter);

    if (outputSourcePosition == SourcePosition.NONE) {
      // Do not record empty mappings.
      return;
    }

    outputSourceInfoByMember.put(memberDescriptor, outputSourcePosition);
  }

  private SourcePosition emit(Runnable codeEmitter) {
    FilePosition startPosition = getCurrentPosition();
    codeEmitter.run();
    FilePosition endPosition = getCurrentPosition();
    if (endPosition.equals(startPosition)) {
      return SourcePosition.NONE;
    }
    return SourcePosition.newBuilder()
        .setStartFilePosition(startPosition)
        .setEndFilePosition(endPosition)
        .build();
  }

  /**
   * Give the SourceMap file construction library enough information to be able to generate all of
   * the required empty group elements between the last mapping and the end of the file.
   */
  private void emitEOF() {
    // TODO(stalcup): switch to generator.setFileLength() when that becomes possible.
    // Emit eof marker
    if (sb.length() != 0) {
      emitWithMapping(
          SourcePosition.newBuilder()
              .setStartFilePosition(
                  FilePosition.newBuilder().setLine(0).setColumn(0).setByteOffset(0).build())
              .setEndFilePosition(
                  FilePosition.newBuilder().setLine(0).setColumn(0).setByteOffset(0).build())
              .build(),
          () -> {});
    }
    finished = true;
  }

  /** Emits a block of code dictated by {@items} followed by a newline. */
  public <T> void emitBlock(List<T> item, Consumer<T> renderer) {
    item.forEach(renderer);
    if (!item.isEmpty()) {
      newLine();
    }
  }

  public SortedMap<SourcePosition, SourcePosition> getMappings() {
    return javaSourceInfoByOutputSourceInfo;
  }

  public ImmutableMap<MemberDescriptor, SourcePosition> getOutputSourceInfoByMember() {
    return ImmutableMap.copyOf(outputSourceInfoByMember);
  }

  public void append(String source) {
    checkState(!finished);
    String indentedSource =
        source.replace(LINE_SEPARATOR, LINE_SEPARATOR + INDENT.repeat(currentIndentation));

    sb.append(indentedSource);
    currentLine += CharMatcher.is(LINE_SEPARATOR_CHAR).countIn(indentedSource);
    int newLineSeperatorIndex = indentedSource.lastIndexOf(LINE_SEPARATOR);
    currentColumn =
        newLineSeperatorIndex == -1
            ? currentColumn + indentedSource.length()
            : indentedSource.length() - newLineSeperatorIndex - 1;
  }

  public void appendLines(String... lines) {
    boolean firstLine = true;
    for (String line : lines) {
      if (!firstLine) {
        newLine();
      }
      append(line);
      firstLine = false;
    }
  }

  public void appendln(String line) {
    append(line);
    newLine();
  }

  public void newLine() {
    append(LINE_SEPARATOR);
  }

  public void indent() {
    currentIndentation++;
  }

  public void unindent() {
    currentIndentation--;
  }

  public String build() {
    emitEOF();
    return sb.toString();
  }

  public void openBrace() {
    append("{");
    indent();
  }

  public void closeBrace() {
    unindent();
    if (sb.charAt(sb.length() - 1) != '{') {
      newLine();
    }
    append("}");
  }

  public void openParens(String text) {
    append("(" + text);
    indent();
  }

  public void closeParens() {
    unindent();
    if (sb.charAt(sb.length() - 1) != '(') {
      newLine();
    }
    append(")");
  }

  private FilePosition getCurrentPosition() {
    return FilePosition.newBuilder()
        .setLine(currentLine)
        .setColumn(currentColumn)
        .setByteOffset(sb.length())
        .build();
  }
}
