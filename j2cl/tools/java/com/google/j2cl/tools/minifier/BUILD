# Description:
#  A minifier library for J2CL generated source. Applicable in dev servers.

load("@rules_java//java:defs.bzl", "java_binary", "java_library")

package(
    default_applicable_licenses = ["//:j2cl_license"],
    licenses = ["notice"],
)
load("@bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

java_binary(
    name = "J2clMinifier",
    main_class = "com.google.j2cl.tools.minifier.J2clMinifier",
    runtime_deps = [":minifier"],
)

java_library(
    name = "minifier",
    srcs = ["J2clMinifier.java"],
    visibility = [
        "//:__pkg__",
        "//tools/javatests/com/google/j2cl/tools:__subpackages__",
    ],
    # Be careful about dependencies!
    # This is integrated by many core infra pieces and should be very lightweight.
    deps = [
        "//third_party:guava",
        "//third_party:jsr305_annotations",
        "//tools/java/com/google/j2cl/tools/rta:code_removal_info_java_proto",
    ],
)
javadoc_library(
    name = "minifier-javadoc",
    srcs = ["J2clMinifier.java"],
    deps = [
        ":minifier"
    ],
)
