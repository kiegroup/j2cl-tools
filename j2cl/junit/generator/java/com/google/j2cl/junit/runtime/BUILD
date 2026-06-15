load("@rules_closure//closure:defs.bzl", "closure_js_library")
load("//build_defs:rules.bzl", "j2cl_library", "j2wasm_library")
load("@rules_java//java:defs.bzl", "java_library", "java_plugin")
load("@google_bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    licenses = ["notice"],
)

closure_js_library(
    name = "internal_j2cl_test_suite",
    testonly = 1,
    srcs = ["j2cl_test_suite.js"],
    visibility = ["//build_defs/internal_do_not_use:__pkg__"],
    deps = [
        ":internal_jasmine_test_converter",
        ":internal_parametrized_test_suite",
        "//third_party:closure_testcase",
        "//third_party:closure_testsuite",
    ],
)

closure_js_library(
    name = "internal_jasmine_test_converter",
    testonly = 1,
    srcs = ["jasmine_test_converter.js"],
    lenient = True,
)

closure_js_library(
    name = "internal_parametrized_test_suite",
    testonly = 1,
    srcs = ["parameterized_test_suite.js"],
    lenient = True,
)

j2wasm_library(
    name = "jsunit_helpers-j2wasm",
    testonly = 1,
    srcs = ["JsUnitHelpers.java"],
    visibility = ["//build_defs/internal_do_not_use:__pkg__"],
    deps = [
        ":internal_assumption_violated_exception-j2wasm",
        "//:jsinterop-annotations-j2wasm",
        "//jre/java:javaemul_internal_annotations-j2wasm",
        "//third_party:closure_testcase",
    ],
)

j2cl_library(
    name = "jsunit_helpers-j2cl",
    testonly = 1,
    srcs = ["JsUnitHelpers.java"],
    visibility = ["//build_defs/internal_do_not_use:__pkg__"],
    deps = [
        ":internal_assumption_violated_exception-j2cl",
        "//:jsinterop-annotations-j2cl",
        "//jre/java:javaemul_internal_annotations-j2cl",
        "//third_party:closure_testcase",
    ],
)

j2cl_library(
    name = "testcase_violator-j2cl",
    testonly = 1,
    srcs = ["TestCaseViolator.java"],
    visibility = ["//build_defs/internal_do_not_use:__pkg__"],
    deps = ["//third_party:junit-j2cl"],
)

j2cl_library(
    name = "internal_assumption_violated_exception-j2cl",
    srcs = ["InternalAssumptionViolatedException.java"],
    visibility = [
        "//build_defs/internal_do_not_use:__pkg__",
        "//junit/emul/java:__pkg__",
    ],
)

java_library(
    name = "internal_assumption_violated_exception",
    srcs = ["InternalAssumptionViolatedException.java"],
    visibility = [
        "//build_defs/internal_do_not_use:__pkg__",
        "//junit/emul/java:__pkg__",
    ],
)

javadoc_library(
    name = "internal_assumption_violated_exception-javadoc",
    srcs = ["InternalAssumptionViolatedException.java"],
)

java_library(
    name = "junit_runtime",
    srcs = glob(["*.java"]),
    resources = glob(["*.js"]),
    deps = [
        "//:jsinterop-annotations",
        "//jre/java:javaemul_internal_annotations",
        "//junit/emul/java:junit_emul-jdk",
    ],
)

javadoc_library(
    name = "junit_runtime-javadoc",
    srcs = glob(["*.java"]),
    testonly = 1,
    deps = [
        "//:jsinterop-annotations",
        "//jre/java:javaemul_internal_annotations",
        "//junit/emul/java:junit_emul-jdk",
    ],
)