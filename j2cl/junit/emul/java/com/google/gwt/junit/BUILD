load("//build_defs:rules.bzl", "j2cl_library", "j2kt_jvm_import", "j2kt_native_library")
load("@google_bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    licenses = ["reciprocal"],  # Eclipse Public License - v 1.0
)

j2cl_library(
    name = "gwttestcase_emul",
    srcs = glob(
        ["**/*.java"],
    ),
    deps = [
        "//junit/emul/java:junit_emul",
    ],
)

java_library(
    name = "gwttestcase_emul-jdk",
    srcs = glob(
        ["**/*.java"],
    ),
    deps = [
        "//junit/emul/java:junit_emul-jdk",
    ],
)

javadoc_library(
    name = "gwttestcase_emul-javadoc",
    srcs = glob(
        ["**/*.java"],
    ),
    deps = [
        ":gwttestcase_emul-jdk",
    ],
)


