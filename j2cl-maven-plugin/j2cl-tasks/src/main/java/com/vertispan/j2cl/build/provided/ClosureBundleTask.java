/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vertispan.j2cl.build.provided;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.debugging.sourcemap.SourceMapConsumerV3;
import com.google.debugging.sourcemap.SourceMapGeneratorV3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerInput;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.deps.ClosureBundler;
import com.google.javascript.jscomp.deps.DependencyInfo;
import com.google.javascript.jscomp.deps.ModuleLoader;
import com.google.javascript.jscomp.deps.SortedDependencies;
import com.google.javascript.jscomp.parsing.parser.FeatureSet;
import com.google.javascript.jscomp.transpile.BaseTranspiler;
import com.google.javascript.jscomp.transpile.Transpiler;
import com.vertispan.j2cl.build.task.CachedPath;
import com.vertispan.j2cl.build.task.ChangedCachedPath;
import com.vertispan.j2cl.build.task.Config;
import com.vertispan.j2cl.build.task.Input;
import com.vertispan.j2cl.build.task.OutputTypes;
import com.vertispan.j2cl.build.task.Project;
import com.vertispan.j2cl.build.task.TaskFactory;
import com.vertispan.j2cl.tools.Closure;
import io.methvin.watcher.hashing.Murmur3F;
import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Emits a single compilationLevel=BUNDLE for this project only, without any dependencies.
 */
@AutoService(TaskFactory.class)
public class ClosureBundleTask extends TaskFactory {

    public static final String BUNDLE_JS_EXTENSION = ".bundle.js";

    @Override
    public String getOutputType() {
        return OutputTypes.BUNDLED_JS;
    }

    @Override
    public String getTaskName() {
        return "default";
    }

    @Override
    public String getVersion() {
        return "1";
    }

    @Override
    public Task resolve(Project project, Config config) {
        final List<Input> js;
        if (project.isJsZip()) {
            js = Collections.singletonList(input(project, OutputTypes.BYTECODE).filter(ClosureTask.PLAIN_JS_SOURCES));
        } else {
            // TODO filter to just JS and sourcemaps? probably not required unless we also get sources
            //      from the actual input source instead of copying it along each step
            js = Stream.of(
                            input(project, OutputTypes.TRANSPILED_JS),
                            input(project, OutputTypes.BYTECODE)
                    )
                    .map(i -> i.filter(ClosureTask.PLAIN_JS_SOURCES))
                    .collect(Collectors.toUnmodifiableList());
        }

        // Consider treating this always as true, since the build doesnt get more costly to be incremental
        boolean incrementalEnabled = config.isIncrementalEnabled();

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        return context -> {
            assert Files.isDirectory(context.outputPath());
            File closureOutputDir = context.outputPath().toFile();

            // even though we're already making the file in our own hash dir, we also want to
            // name the file by a hash so it has a unique filename based on its contents
            String fileNameKey = project.getKey().replaceAll("[^\\-_a-zA-Z0-9.]", "-");
            String outputFile = closureOutputDir + "/" + fileNameKey + ".js";

            Path outputFilePath = Paths.get(outputFile);
            if (js.stream().map(Input::getFilesAndHashes).flatMap(Collection::stream).findAny().isEmpty()) {
                // if there are no js sources, write an empty file and exit
                Files.createFile(outputFilePath);
                return;// nothing to do
            }

            // copy the sources locally so that we can create usable sourcemaps
            //TODO consider a soft link
            File sources = new File(closureOutputDir, Closure.SOURCES_DIRECTORY_NAME);
            for (Path path : js.stream().map(Input::getParentPaths).flatMap(Collection::stream).collect(Collectors.toUnmodifiableList())) {
                FileUtils.copyDirectory(path.toFile(), sources);
            }

            List<DependencyInfoAndSource> dependencyInfos = new ArrayList<>();
            Compiler jsCompiler = new Compiler(System.err);//TODO before merge, write this to the log

            Path sourcesPath = context.outputPath().resolve(Closure.SOURCES_DIRECTORY_NAME);
            if (incrementalEnabled && context.lastSuccessfulOutput().isPresent()) {
                // collect any dep info from disk for existing files
                final Map<String, DependencyInfoAndSource> depInfoMap;
                Path lastOutput = context.lastSuccessfulOutput().get();
                try (InputStream inputStream = Files.newInputStream(lastOutput.resolve("depInfo.json"))) {
                    Type listType = new TypeToken<List<DependencyInfoFormat>>() {
                    }.getType();
                    List<DependencyInfoFormat> deps = gson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), listType);
                    depInfoMap = deps.stream()
                            .map(info -> new DependencyInfoAndSource(
                                    info,
                                    () -> Files.readString(lastOutput.resolve(Closure.SOURCES_DIRECTORY_NAME).resolve(info.getName())))
                            )
                            .collect(Collectors.toMap(DependencyInfo::getName, Function.identity()));
                }

                // create new dep info for any added/modified file
                for (Input jsInput : js) {
                    for (ChangedCachedPath change : jsInput.getChanges()) {
                        if (change.changeType() == ChangedCachedPath.ChangeType.REMOVED) {
                            depInfoMap.remove(change.getSourcePath().toString());
                        } else {
                            // ADD or MODIFY
                            CompilerInput input = new CompilerInput(SourceFile.builder()
                                    .withPath(sourcesPath.resolve(change.getSourcePath()))
                                    .withOriginalPath(change.getSourcePath().toString())
                                    .build());
                            input.setCompiler(jsCompiler);
                            depInfoMap.put(
                                    change.getSourcePath().toString(),
                                    new DependencyInfoAndSource(input, input::getCode)
                            );
                        }
                    }
                }

                // no need to expand to include other files, since this is only computed locally

                // assign the dep info and sources we have
                dependencyInfos.addAll(depInfoMap.values());
            } else {
                //non-incremental, read everything
                for (Input jsInput : js) {
                    for (CachedPath path : jsInput.getFilesAndHashes()) {
                        CompilerInput input = new CompilerInput(SourceFile.builder()
                                .withPath(sourcesPath.resolve(path.getSourcePath()))
                                .withOriginalPath(path.getSourcePath().toString())
                                .build());
                        input.setCompiler(jsCompiler);

                        dependencyInfos.add(new DependencyInfoAndSource(input, input::getCode));
                    }
                }
            }

            // re-sort that full collection
            SortedDependencies<DependencyInfoAndSource> sorter = new SortedDependencies<>(dependencyInfos);


            // TODO optional/stretch-goal find first change in the list, so we can keep old prefix of bundle output

            SourceMapGeneratorV3 sourceMapGenerator = new SourceMapGeneratorV3();

            // rebundle all (optional: remaining) files using this already handled sort
            ClosureBundler bundler = new ClosureBundler(Transpiler.NULL, new BaseTranspiler(
                    new BaseTranspiler.CompilerSupplier(
                            CompilerOptions.LanguageMode.ECMASCRIPT_NEXT.toFeatureSet().without(FeatureSet.Feature.MODULES),
                            ModuleLoader.ResolutionMode.BROWSER,
                            ImmutableList.copyOf(js.stream()
                                    .map(Input::getParentPaths)
                                    .flatMap(Collection::stream)
                                    .map(Path::toString)
                                    .collect(Collectors.toUnmodifiableList())),
                            ImmutableMap.of()
                    ),
                    ""
            )).useEval(false);

            String sourcemapOutFileName = fileNameKey + ".bundle.js.map";

            try (OutputStream outputStream = Files.newOutputStream(outputFilePath);
                 BufferedWriter bundleOut = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                 LineCountingWriter writer = new LineCountingWriter(bundleOut)) {
                for (DependencyInfoAndSource info : sorter.getSortedList()) {
                    String code = info.getSource();
                    String name = info.getName();
                    String sourcemapContents = info.loadSourcemap(sourcesPath);

                    //TODO do we actually need this?
                    if (Compiler.isFillFileName(name) && code.isEmpty()) {
                        continue;
                    }

                    writer.append("//").append(name).append("\n");

                    if (sourcemapContents != null) {
                        sourceMapGenerator.setStartingPosition(writer.getLine(), 0);
                        SourceMapConsumerV3 section = new SourceMapConsumerV3();
                        section.parse(sourcemapContents);
                        section.visitMappings((sourceName, symbolName, sourceStartPosition, startPosition, endPosition) -> sourceMapGenerator.addMapping(Paths.get(name).resolveSibling(sourceName).toString(), symbolName, sourceStartPosition, startPosition, endPosition));
                        for (String source : section.getOriginalSources()) {
                            String content = Files.readString(sourcesPath.resolve(name).resolveSibling(source));
                            sourceMapGenerator.addSourcesContent(Paths.get(name).resolveSibling(source).toString(), content);
                        }
                    }

                    // append this file and a comment where it came from
                    bundler.withPath(name).appendTo(writer, info, code);
                    writer.append("\n");
                }

                // write a reference to our new sourcemaps
//                writer.append("// " + writer.getLine()).append("\n");
                writer.append("//# sourceMappingURL=").append(sourcemapOutFileName).append('\n');
            }

            // TODO hash in the name
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath.resolveSibling(sourcemapOutFileName));
                 BufferedWriter smOut = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                sourceMapGenerator.appendTo(smOut, fileNameKey);
            }

            // append dependency info to deserialize on some incremental rebuild
            try (OutputStream outputStream = Files.newOutputStream(context.outputPath().resolve("depInfo.json"));
                 BufferedWriter jsonOut = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                List<DependencyInfoFormat> jsonList = sorter.getSortedList().stream()
                        .map(DependencyInfoFormat::new)
                        .collect(Collectors.toUnmodifiableList());
                gson.toJson(jsonList, jsonOut);
            }

            // hash the file itself, rename to include that hash
            Murmur3F murmur = new Murmur3F();
            try (InputStream is = new BufferedInputStream(Files.newInputStream(outputFilePath))) {
                int b;
                while ((b = is.read()) != -1) {
                    murmur.update(b);
                }
            }
            Files.move(outputFilePath, outputFilePath.resolveSibling(fileNameKey + "-" + murmur.getValueHexString() + BUNDLE_JS_EXTENSION));
            //TODO when back to keyboard rename sourcemap? is that a thing we need to do?
        };
    }


    public static class LineCountingWriter extends FilterWriter {
        private int line;
        protected LineCountingWriter(Writer out) {
            super(out);
        }

        public int getLine() {
            return line;
        }

        @Override
        public void write(int c) throws IOException {
            if (c == '\n') {
                line++;
            }
            super.write(c);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (char c : cbuf) {
                if (c == '\n') {
                    line++;
                }
            }
            super.write(cbuf, off, len);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            str.chars().skip(off).limit(len).forEach(c -> {
                if (c == '\n') {
                    line++;
                }
            });
            super.write(str, off, len);
        }

        @Override
        public void write(char[] cbuf) throws IOException {
            for (char c : cbuf) {
                if (c == '\n') {
                    line++;
                }
            }
            super.write(cbuf);
        }
    }

    public interface SourceSupplier {
        String get() throws IOException;
    }
    public static class DependencyInfoAndSource implements DependencyInfo {
        private final DependencyInfo delegate;
        private final SourceSupplier sourceSupplier;

        public DependencyInfoAndSource(DependencyInfo delegate, SourceSupplier sourceSupplier) {
            this.delegate = delegate;
            this.sourceSupplier = sourceSupplier;
        }

        public String getSource() throws IOException {
            return sourceSupplier.get();
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public String getPathRelativeToClosureBase() {
            return delegate.getPathRelativeToClosureBase();
        }

        @Override
        public ImmutableList<String> getProvides() {
            return delegate.getProvides();
        }

        @Override
        public ImmutableList<Require> getRequires() {
            return delegate.getRequires();
        }

        @Override
        public ImmutableList<String> getRequiredSymbols() {
            //deliberately overriding the base impl
            return delegate.getRequiredSymbols();
        }

        @Override
        public ImmutableList<String> getTypeRequires() {
            return delegate.getTypeRequires();
        }

        @Override
        public ImmutableMap<String, String> getLoadFlags() {
            return delegate.getLoadFlags();
        }

        @Override
        public boolean isEs6Module() {
            return delegate.isEs6Module();
        }

        @Override
        public boolean isGoogModule() {
            return delegate.isGoogModule();
        }

        @Override
        public boolean getHasExternsAnnotation() {
            return delegate.getHasExternsAnnotation();
        }

        @Override
        public boolean getHasNoCompileAnnotation() {
            return delegate.getHasNoCompileAnnotation();
        }

        public String loadSourcemap(Path outPath) throws IOException {
            String sourceMappingUrlMarker = "//# sourceMappingURL=";
            int offset = getSource().lastIndexOf(sourceMappingUrlMarker);
            if (offset == -1) {
                return null;
            }
            int urlPos = offset + sourceMappingUrlMarker.length();
            String sourcemapName = getSource().substring(urlPos).split("\\s")[0];
            return Files.readString(outPath.resolve(getName()).resolveSibling(sourcemapName));
        }
    }

    public static class DependencyInfoFormat implements DependencyInfo {
        private String name;
        //        private String pathRelativeToClosureBase = name;
        private List<String> provides;
        //        private List<RequireFormat> requires; //skipping requires as it isnt used by the dep sorter
        private List<String> requiredSymbols;
        private List<String> typeRequires;
        private Map<String, String> loadFlags;
        private boolean hasExternsAnnotation;
        private boolean hasNoCompileAnnotation;

        public DependencyInfoFormat() {

        }

        public DependencyInfoFormat(DependencyInfo info) {
            setName(info.getName());
            setHasExternsAnnotation(info.getHasExternsAnnotation());
            setHasNoCompileAnnotation(info.getHasExternsAnnotation());
            setProvides(info.getProvides());
            setLoadFlags(info.getLoadFlags());
            setTypeRequires(info.getTypeRequires());
            setRequiredSymbols(info.getRequiredSymbols());
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getPathRelativeToClosureBase() {
            return getName();
        }

        public ImmutableList<String> getProvides() {
            return ImmutableList.copyOf(provides);
        }

        public void setProvides(List<String> provides) {
            this.provides = provides;
        }

        public ImmutableList<Require> getRequires() {
            return ImmutableList.of();
        }

        @Override
        public ImmutableList<String> getRequiredSymbols() {
            return ImmutableList.copyOf(requiredSymbols);
        }

        public void setRequiredSymbols(List<String> requiredSymbols) {
            this.requiredSymbols = requiredSymbols;
        }

        public ImmutableList<String> getTypeRequires() {
            return ImmutableList.copyOf(typeRequires);
        }

        public void setTypeRequires(List<String> typeRequires) {
            this.typeRequires = typeRequires;
        }

        public ImmutableMap<String, String> getLoadFlags() {
            return ImmutableMap.copyOf(loadFlags);
        }

        public void setLoadFlags(Map<String, String> loadFlags) {
            this.loadFlags = loadFlags;
        }

        public boolean getHasExternsAnnotation() {
            return hasExternsAnnotation;
        }

        public void setHasExternsAnnotation(boolean hasExternsAnnotation) {
            this.hasExternsAnnotation = hasExternsAnnotation;
        }

        public boolean getHasNoCompileAnnotation() {
            return hasNoCompileAnnotation;
        }

        public void setHasNoCompileAnnotation(boolean hasNoCompileAnnotation) {
            this.hasNoCompileAnnotation = hasNoCompileAnnotation;
        }
    }
}
