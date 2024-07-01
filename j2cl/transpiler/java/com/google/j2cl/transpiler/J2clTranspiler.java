/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.j2cl.transpiler;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.j2cl.common.Problems;
import com.google.j2cl.transpiler.ast.CompilationUnit;
import com.google.j2cl.transpiler.ast.FieldDescriptor;
import com.google.j2cl.transpiler.ast.Library;
import com.google.j2cl.transpiler.ast.MemberDescriptor;
import com.google.j2cl.transpiler.ast.MethodDescriptor;
import com.google.j2cl.transpiler.ast.TypeDeclaration;
import com.google.j2cl.transpiler.passes.LibraryNormalizationPass;
import com.google.j2cl.transpiler.passes.NormalizationPass;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/** Translation tool for generating JavaScript source files from Java sources. */
public class J2clTranspiler {

  /** Runs the entire J2CL pipeline. */
  public static void transpile(J2clTranspilerOptions options, Problems problems) {
    // Compiler has no static state, but rather uses thread local variables.
    // Because of this, we invoke the compiler on a different thread each time.
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> result =
        executorService.submit(() -> new J2clTranspiler(options, problems).transpileImpl());
    // Shutdown the executor service since it will only run a single transpilation. If not shutdown
    // it prevents the JVM from ending the process (see Executors.newFixedThreadPool()). This is not
    // normally observed since the transpiler in normal circumstances ends with System.exit() which
    // ends all threads. But when the transpilation throws an exception, the exception propagates
    // out of main() and the process lingers due the live threads from these executors.
    executorService.shutdown();

    try {
      Uninterruptibles.getUninterruptibly(result);
    } catch (ExecutionException e) {
      // Try unwrapping the cause...
      Throwables.throwIfUnchecked(e.getCause());
      throw new AssertionError(e.getCause());
    }
  }

  private final J2clTranspilerOptions options;
  private final Problems problems;

  private J2clTranspiler(J2clTranspilerOptions options, Problems problems) {
    this.options = options;
    this.problems = problems;
  }

  private void transpileImpl() {
    if (options.getBackend().isWasm()) {
      // TODO(b/178738483): Remove hack that makes mangling backend dependent.
      MemberDescriptor.setWasmManglingPatterns();
      if (options.getWasmEnableNonNativeJsEnum()) {
        // TODO(b/181615162): Remove hack that makes it possible to ignore JsEnum in Wasm.
        TypeDeclaration.setIgnoreNativeJsEnumAnnotations();
      } else {
        // TODO(b/181615162): Remove hack that makes it possible to ignore JsEnum in Wasm.
        TypeDeclaration.setIgnoreJsEnumAnnotations();
      }
      // TODO(b/317164851): Remove hack that makes jsinfo ignored for non-native types in Wasm.
      FieldDescriptor.setIgnoreNonNativeJsInfo();
      MethodDescriptor.setIgnoreNonNativeJsInfo();
      // TODO(b/340930928): This is a temporary hack since JsFunction is not supported in Wasm.
      TypeDeclaration.setIgnoreJsFunctionAnnotations();
      // TODO(b/178738483): Remove hack that makes it possible to ignore DoNotAutobox in Wasm.
      MethodDescriptor.ParameterDescriptor.setIgnoreDoNotAutoboxAnnotations();
    }
    Library library = options.getFrontend().getLibrary(options, problems);
    if (!library.isEmpty()) {
      desugarLibrary(library);
      checkLibrary(library);
      normalizeLibrary(library);
    }
    options.getBackend().generateOutputs(options, library, problems);
  }

  private void desugarLibrary(Library library) {
    runPasses(library, options.getBackend().getDesugaringPassFactories());
  }

  private void checkLibrary(Library library) {
    // Check backend-specific restrictions.
    options.getBackend().checkRestrictions(options, library, problems);

    problems.abortIfHasErrors();
  }

  private void normalizeLibrary(Library library) {
    runPasses(library, options.getBackend().getPassFactories(options));
  }

  private void runPasses(
      Library library, ImmutableList<Supplier<NormalizationPass>> passFactories) {
    for (Supplier<NormalizationPass> passFactory : passFactories) {
      NormalizationPass pass = instantiatePass(passFactory);
      if (pass instanceof LibraryNormalizationPass) {
        ((LibraryNormalizationPass) pass).execute(library);
        problems.abortIfHasErrors();
        continue;
      }
      for (CompilationUnit compilationUnit : library.getCompilationUnits()) {
        instantiatePass(passFactory).execute(compilationUnit);
      }
      problems.abortIfHasErrors();
    }
  }

  private NormalizationPass instantiatePass(Supplier<NormalizationPass> passFactory) {
    NormalizationPass pass = passFactory.get();
    pass.setProblems(problems);
    return pass;
  }
}
