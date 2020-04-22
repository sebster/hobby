def _spring_repackage_impl(ctx):
    transitive_deps = ctx.attr.app[JavaInfo].transitive_runtime_deps

    repackager_args = ctx.actions.args()
    repackager_args.add("--main-class", ctx.attr.main_class)
    repackager_args.add("--source-jar", ctx.file.app)
    repackager_args.add("--output", ctx.outputs.jar)
    repackager_args.add_joined("--runtime-dependencies", transitive_deps, join_with = ",")

    ctx.actions.run(
        inputs = depset([ctx.file.app], transitive = [transitive_deps]),
        outputs = [ctx.outputs.jar],
        progress_message = "Running repackager",
        executable = ctx.executable._repackager,
        arguments = [repackager_args],
    )
    return JavaInfo(output_jar = ctx.outputs.jar, compile_jar = ctx.outputs.jar)

spring_repackage = rule(
    implementation = _spring_repackage_impl,
    attrs = {
        "main_class": attr.string(),
        "app": attr.label(providers = [JavaInfo], allow_single_file = True),
        "_repackager": attr.label(
            default = "//tools/rules_spring:spring_boot_repackager",
            executable = True,
            cfg = "host",
        ),
    },
    outputs = {
        "jar": "%{name}.jar",
    },
)

def spring_boot_jar(name, main_class, app, visibility = None, **kwargs):
    spring_repackage(
        name = name,
        main_class = main_class,
        app = app,
        visibility = visibility,
    )
