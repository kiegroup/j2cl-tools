load("@bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    default_visibility = ["//transpiler:__subpackages__"],
    licenses = ["notice"],
)

java_library(
    name = "wasm",
    srcs = glob(["*.java"]),
    deps = [
        "//third_party:auto_value",
        "//third_party:gson",
        "//third_party:guava",
        "//third_party:jsr305_annotations",
        "//transpiler/java/com/google/j2cl/common",
        "//transpiler/java/com/google/j2cl/transpiler/ast",
        "//transpiler/java/com/google/j2cl/transpiler/backend/closure",
        "//transpiler/java/com/google/j2cl/transpiler/backend/common",
    ],
)

javadoc_library(
    name = "wasm-javadoc",
    srcs = glob(["*.java"]),
    deps = [
        ":wasm",
    ],
)
