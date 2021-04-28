"""j2wasm_application build macro

Takes Java source, translates it into Wasm.
This is an experimental tool and should not be used.
"""

load(":provider.bzl", "J2wasmInfo")

def _impl_j2wasm_application(ctx):
    deps = ctx.attr.deps + [ctx.attr._jre]
    srcs = _get_transitive_srcs(deps)
    classpath = _get_transitive_classpath(deps)

    transpile_out = ctx.actions.declare_file(ctx.label.name + ".zip")
    args = ctx.actions.args()
    args.use_param_file("@%s", use_always = True)
    args.set_param_file_format("multiline")
    args.add_joined("-classpath", classpath, join_with = ctx.configuration.host_path_separator)
    args.add("-output", transpile_out)
    args.add("-experimentalBackend", "WASM")
    args.add_all(ctx.attr.entry_points, before_each = "-experimentalGenerateWasmExport")
    args.add_all(ctx.attr.transpiler_args)

    args.add_all(srcs)

    enable_wasm_checks = ctx.var.get("j2cl_wasm_checks", None) == "1"

    ctx.actions.run(
        progress_message = "Transpiling to WASM %s" % ctx.label,
        inputs = depset(transitive = [srcs, classpath]),
        outputs = [transpile_out],
        executable = ctx.executable._j2cl_transpiler,
        arguments = ["--jvm_flag=-Dj2cl.enable_wasm_checks=" + str(enable_wasm_checks), args],
        env = dict(LANG = "en_US.UTF-8"),
        execution_requirements = {"supports-workers": "1"},
        mnemonic = "J2wasm",
    )

    # unzip wat file
    ctx.actions.run_shell(
        progress_message = "Unzipping wat file",
        inputs = [transpile_out],
        outputs = [ctx.outputs.wat],
        command = (
            "tmp=$(mktemp -d);" +
            "%s -q %s -d $tmp;" % (ctx.executable._zip.path, transpile_out.path) +
            "cp $tmp/module.wat %s;" % ctx.outputs.wat.path +
            "rm -R $tmp;"
        ),
        tools = [ctx.executable._zip],
        mnemonic = "J2wasmZip",
    )

    args = ctx.actions.args()
    args.add("--enable-exception-handling")
    args.add("--enable-typed-function-references")
    args.add("--enable-gc")
    args.add("--enable-reference-types")
    args.add("--enable-sign-ext")
    args.add("--enable-nontrapping-float-to-int")
    args.add_all(ctx.attr.binaryen_args)
    args.add("-o", ctx.outputs.wasm)
    args.add(ctx.outputs.wat)

    ctx.actions.run(
        executable = ctx.executable.binaryen,
        arguments = [args],
        inputs = [ctx.outputs.wat],
        outputs = [ctx.outputs.wasm],
        mnemonic = "J2wasm",
        progress_message = "Compiling wat2wasm",
    )

    # Trigger a parallel Javac build to provide better error messages than JDT.
    ctx.actions.run_shell(
        outputs = [ctx.outputs.validate],
        arguments = [ctx.outputs.validate.path],
        command = "touch $1",
        inputs = _trigger_javac_build(ctx.attr.deps),
    )

def _get_transitive_srcs(deps):
    return depset(transitive = [d[J2wasmInfo]._private_.transitive_srcs for d in deps])

def _get_transitive_classpath(deps):
    return depset(transitive = [d[J2wasmInfo]._private_.transitive_classpath for d in deps])

def _trigger_javac_build(deps):
    return depset(transitive = [d[J2wasmInfo]._private_.java_info.transitive_runtime_jars for d in deps])

_j2wasm_application = rule(
    implementation = _impl_j2wasm_application,
    attrs = {
        "deps": attr.label_list(providers = [J2wasmInfo]),
        "entry_points": attr.string_list(),
        "binaryen_args": attr.string_list(),
        "transpiler_args": attr.string_list(),
        "binaryen": attr.label(
            cfg = "host",
            executable = True,
        ),
        "_jre": attr.label(default = Label("//build_defs/internal_do_not_use:j2wasm_jre")),
        "_j2cl_transpiler": attr.label(
            default = Label(
                "//build_defs/internal_do_not_use:BazelJ2clBuilder",
            ),
            cfg = "host",
            executable = True,
        ),
        "_zip": attr.label(
            cfg = "host",
            executable = True,
            default = Label("//third_party/unzip"),
        ),
    },
    outputs = {
        "wat": "%{name}.wat",
        "wasm": "%{name}.wasm",
        "validate": "%{name}.validate",
    },
)

def j2wasm_application(name, **kwargs):
    _j2wasm_application(
        name = name,
        binaryen_args = ["-O"],
        transpiler_args = ["-experimentalWasmRemoveAssertStatement"],
        binaryen = "//third_party/binaryen:wasm-opt",
        **kwargs
    )
    _j2wasm_application(
        name = name + "_dev",
        binaryen = "//third_party/binaryen:wasm-as",
        **kwargs
    )