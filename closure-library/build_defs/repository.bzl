"""Bazel rule for loading external repository deps for generating externs like in elemental2."""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

_JSINTEROP_GENERATOR_VERSION = "master"
_CLOSURE_COMPILER_VERSION = "master"

def load_elemental2_repo_deps():

    http_archive(
        name = "com_google_jsinterop_generator",
        url = "https://github.com/google/jsinterop-generator/archive/%s.zip" % _JSINTEROP_GENERATOR_VERSION,
        strip_prefix = "jsinterop-generator-%s" % _JSINTEROP_GENERATOR_VERSION,
    )