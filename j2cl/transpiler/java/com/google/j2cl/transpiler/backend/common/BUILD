load("@rules_java//java:defs.bzl", "java_library")
load("@bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    default_visibility = ["//transpiler:__subpackages__"],
    licenses = ["notice"],
)

java_library(
    name = "common",
    srcs = glob(["*.java"]),
    deps = [
        "//third_party:guava",
        "//transpiler/java/com/google/j2cl/common",
        "//transpiler/java/com/google/j2cl/transpiler/ast",
    ],
)

javadoc_library(
    name = "common-javadoc",
    srcs = glob(["*.java"]),
    deps = [
        ":common",
    ],
)
