"""Macro to use for loading the J2CL repository"""

load("@bazel_skylib//lib:versions.bzl", "versions")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_jar")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")
load("@io_bazel_rules_closure//closure:repositories.bzl", "rules_closure_dependencies")
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")

_MAVEN_CENTRAL_URLS = ["https://repo1.maven.org/maven2/"]

def setup_j2cl_workspace(**kwargs):
    """Load all dependencies needed for J2CL."""

    versions.check("5.4.0")  # The version J2CL currently have a CI setup for.

    rules_closure_dependencies(
        omit_com_google_auto_common = True,
        **kwargs
    )

    jvm_maven_import_external(
        name = "com_google_auto_common",
        artifact = "com.google.auto:auto-common:1.1.2",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "bfe85e517250fc208afd2b031a2ba80f26529c92536484841b4a60661ca1e3f5",
    )

    jvm_maven_import_external(
        name = "com_google_auto_service_annotations",
        artifact = "com.google.auto.service:auto-service-annotations:1.0",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "44752893119fdaf01b4c5ee74e46e5dab86f2dcda18114c562f877355c6ed26e",
    )

    jvm_maven_import_external(
        name = "com_google_auto_service",
        artifact = "com.google.auto.service:auto-service:1.0",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "4ae44dd05b49a1109a463c0d2aaf920c24f76d1e996bb89f29481c4ff75ec526",
    )

    # We cannot replace com_google_jsinterop_annotations so choose a different name
    http_archive(
        name = "com_google_jsinterop_annotations-j2cl",
        urls = ["https://github.com/google/jsinterop-annotations/archive/d2b3aee14b617a81570b788f981926a829ac892c.zip"],
        strip_prefix = "jsinterop-annotations-d2b3aee14b617a81570b788f981926a829ac892c",
        sha256 = "4164229681bcaf3d130b6c4f463bc345af69de5ad548120e9818f31c70142717",
    )

    http_archive(
      name = "bazel_common_javadoc",
      strip_prefix = "bazel-common-213ecb74698948ce4e1597c9247f2ab8933553d7/tools/javadoc",
      urls = ["https://github.com/google/bazel-common/archive/213ecb74698948ce4e1597c9247f2ab8933553d7.zip"],
      sha256 = "dcefae7d8561f684f305223aad530e5dccb4e8068a653edfc2c89abe9570d2a6",
    )

    jvm_maven_import_external(
        name = "j2objc_annotations",
        artifact = "com.google.j2objc:j2objc-annotations:1.3",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    jvm_maven_import_external(
        name = "org_jspecify",
        artifact = "org.jspecify:jspecify:0.3.0",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    jvm_maven_import_external(
        name = "org_apache_commons_collections",
        artifact = "commons-collections:commons-collections:3.2.2",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "eeeae917917144a68a741d4c0dff66aa5c5c5fd85593ff217bced3fc8ca783b8",
    )

    jvm_maven_import_external(
        name = "org_apache_commons_lang2",
        artifact = "commons-lang:commons-lang:2.6",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "50f11b09f877c294d56f24463f47d28f929cf5044f648661c0f0cfbae9a2f49c",
    )

    jvm_maven_import_external(
        name = "com_google_escapevelocity",
        artifact = "com.google.escapevelocity:escapevelocity:1.1",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "37e76e4466836dedb864fb82355cd01c3bd21325ab642d89a0f759291b171231",
    )

    jvm_maven_import_external(
        name = "org_junit",
        artifact = "junit:junit:4.13.2",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    jvm_maven_import_external(
        name = "com_google_testing_compile",
        artifact = "com.google.testing.compile:compile-testing:0.15",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    jvm_maven_import_external(
        name = "org_mockito",
        artifact = "org.mockito:mockito-all:1.9.5",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    jvm_maven_import_external(
        name = "com_google_truth",
        artifact = "com.google.truth:truth:1.0",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
    )

    # TODO(b/135461024): for now J2CL uses a prepackaged version of javac. But in the future it
    # might be better to tie in to the Java platform in bazel and control the version there.
    jvm_maven_import_external(
        name = "com_sun_tools_javac",
        artifact = "com.google.errorprone:javac:9+181-r4173-1",
        server_urls = _MAVEN_CENTRAL_URLS,
        licenses = ["notice"],
        artifact_sha256 = "1d8d347a0e1579f3fc86ac04d1974e489afc66357f0009ac9804a7ac30912ed6",
    )

    # Eclipse JARs listed at
    # http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/

    http_jar(
        name = "org_eclipse_jdt_content_type",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.core.contenttype_3.7.700.v20200517-1644.jar",
        sha256 = "af418cced47512a7cad606ea9a1114267bc224387abcedd639bae8d3a7fb10b9",
    )

    http_jar(
        name = "org_eclipse_jdt_jobs",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.core.jobs_3.10.800.v20200421-0950.jar",
        sha256 = "4d0042425dcc3655c08654351c08b1645ccb309ab5de45743455bfce4849e917",
    )

    http_jar(
        name = "org_eclipse_jdt_resources",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.core.resources_3.13.700.v20200209-1624.jar",
        sha256 = "ce021447dbea30a4e5ddb3f52534cd2794fb52855071b8dcf257b936ab162168",
    )

    http_jar(
        name = "org_eclipse_jdt_runtime",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.core.runtime_3.18.0.v20200506-2143.jar",
        sha256 = "b5aebc31d480efff38f910a6eab791c2de7b126a47d260252e097b5a27bd0165",
    )

    http_jar(
        name = "org_eclipse_jdt_equinox_common",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.equinox.common_3.12.0.v20200504-1602.jar",
        sha256 = "761f9175b9d294d122c1aa92048688f0b71dd81e808c64cbb245ca7539950716",
    )

    http_jar(
        name = "org_eclipse_jdt_equinox_preferences",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.equinox.preferences_3.8.0.v20200422-1833.jar",
        sha256 = "ca62478a40cffdfe9a10dcfb9f8fada760a93644a7de2c2d1897235f67f57b42",
    )

    http_jar(
        name = "org_eclipse_jdt_compiler_apt",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.jdt.apt.core_3.6.600.v20200529-1546.jar",
        sha256 = "0559677c8d0528fbdfa3a82b4a16661894a9b64a342e418809c64945bb5d3ef1",
    )

    http_jar(
        name = "org_eclipse_jdt_core",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.jdt.core_3.22.0.v20200530-2032.jar",
        sha256 = "af89d348c24917506675767fc1534a0d673355d334fbfadd264b9e45ccd9c34c",
    )

    http_jar(
        name = "org_eclipse_jdt_osgi",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.osgi_3.15.300.v20200520-1959.jar",
        sha256 = "a3544cde6924babf8aff8323f7452ace232d01d040e20d9f9f43027d7b945424",
    )

    http_jar(
        name = "org_eclipse_jdt_text",
        url = "http://download.eclipse.org/eclipse/updates/4.16/R-4.16-202006040540/plugins/org.eclipse.text_3.10.200.v20200428-0633.jar",
        sha256 = "83ce07ec2058d8d629feb4e269216e286560b0e4587dea883f4e16b64ea51cad",
    )

    kotlin_repositories(
        compiler_release = {
            "urls": [
                "https://github.com/JetBrains/kotlin/releases/download/v1.6.10/kotlin-compiler-1.6.10.zip",
            ],
            "sha256": "432267996d0d6b4b17ca8de0f878e44d4a099b7e9f1587a98edc4d27e76c215a",
        },
    )
    kt_register_toolchains()

    # Required by protobuf_java_util
    native.bind(
        name = "guava",
        actual = "@com_google_guava",
    )

    # Required by protobuf_java_util, also needed by generator
    native.bind(
        name = "gson",
        actual = "@com_google_code_gson",
    )

    # Required by protobuf_java_util
    native.bind(
        name = "error_prone_annotations",
        actual = "@com_google_errorprone_error_prone_annotations",
    )
