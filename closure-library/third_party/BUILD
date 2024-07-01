package(default_visibility = [
    "//:__subpackages__",
])

licenses(["notice"])  # Apache2

# Already-created elemental2 java apis, with metadata to let us reference them and share classes like Array, Element

java_library(
    name = "gwt-array-stamper",
    srcs = [
        "@org_gwtproject_gwt//user:super/com/google/gwt/emul/javaemul/internal/ArrayStamper.java",
    ],
)

alias(
    name = "elemental2-core",
    actual = "@com_google_elemental2//:elemental2-core",
)
alias(
    name = "elemental2-dom",
    actual = "@com_google_elemental2//:elemental2-dom",
)
alias(
    name = "elemental2-webgl",
    actual = "@com_google_elemental2//:elemental2-webgl",
)
alias(
    name = "elemental2-promise",
    actual = "@com_google_elemental2//:elemental2-promise",
)
alias(
    name = "elemental2-core-j2cl",
    actual = "@com_google_elemental2//:elemental2-core-j2cl",
)
alias(
    name = "elemental2-dom-j2cl",
    actual = "@com_google_elemental2//:elemental2-dom-j2cl",
)
alias(
    name = "elemental2-webgl-j2cl",
    actual = "@com_google_elemental2//:elemental2-webgl-j2cl",
)
alias(
    name = "elemental2-promise-j2cl",
    actual = "@com_google_elemental2//:elemental2-promise-j2cl",
)
alias(
    name = "elemental2-core__internal_src_generated",
    actual = "@com_google_elemental2//:elemental2-core__internal_src_generated",
)
alias(
    name = "elemental2-dom__internal_src_generated",
    actual = "@com_google_elemental2//:elemental2-dom__internal_src_generated",
)
alias(
    name = "elemental2-webgl__internal_src_generated",
    actual = "@com_google_elemental2//:elemental2-webgl__internal_src_generated",
)
alias(
    name = "elemental2-promise__internal_src_generated",
    actual = "@com_google_elemental2//:elemental2-promise__internal_src_generated",
)
