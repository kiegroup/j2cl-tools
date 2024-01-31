#!/bin/bash
# Copyright 2019 Google Inc. All Rights Reserved
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS-IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -e

# Build and test Hello World sample in its own workspace
(cd samples/helloworld && bazel test ...)

if [[ $1 == "CI" ]]; then
  # Build Guava sample in its own workspace
  (cd samples/guava && bazel build ...)

  # Build Wasm sample in its own workspace
  # Note that since binaryen dep is not available, we only build the wat file.
  (cd samples/wasm && bazel build src/main/java/com/google/j2cl/samples/wasm:app.wat)
fi
