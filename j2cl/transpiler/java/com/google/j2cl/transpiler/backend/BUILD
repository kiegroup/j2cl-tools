# Description:
#   This package contains backend driver code.

load("@rules_java//java:defs.bzl", "java_library")
load("@bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    default_visibility = [
        "//transpiler:__subpackages__",
    ],
    licenses = ["notice"],
)

java_library(
    name = "backend",
    srcs = glob(["*.java"]),
    deps = [
        "//third_party:guava",
        "//third_party:jsr305_annotations",
        "//transpiler/java/com/google/j2cl/common",
        "//transpiler/java/com/google/j2cl/transpiler/ast",
        "//transpiler/java/com/google/j2cl/transpiler/backend/closure",
        "//transpiler/java/com/google/j2cl/transpiler/backend/kotlin",
        "//transpiler/java/com/google/j2cl/transpiler/backend/wasm",
        "//transpiler/java/com/google/j2cl/transpiler/passes",
    ],
)

javadoc_library(
    name = "backend-javadoc",
    srcs = glob(["*.java"]),
    deps = [
        ":backend",
    ],
)
