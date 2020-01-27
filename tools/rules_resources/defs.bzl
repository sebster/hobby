load("@bazel_skylib//lib:paths.bzl", "paths")

def resource_jar(name, jar = None, **kwargs):
    _resource_jar(
        name = name,
        jar = jar or "%s.jar" % name,
        **kwargs
    )

def _zipper_arg_strip_package(file):
    return "%s=%s" % (paths.relativize(file.short_path, file.owner.package), file.path)

def _resource_jar_impl(ctx):
    outjar = ctx.outputs.jar
    jar_args = ctx.actions.args()
    jar_args.add_all(["c", ctx.outputs.jar])

    jar_args.add_all(
        ctx.files.resources,
        map_each = _zipper_arg_strip_package,
        format_each = paths.join(ctx.attr.root, "%s"),
    )

    for resourcesTarget, location in ctx.attr.absolute_resources.items():
        jar_args.add_all(resourcesTarget.files, format_each = location + "=%s")

    ctx.actions.run(
        executable = ctx.executable._zipper,
        arguments = [jar_args],
        inputs = depset(ctx.files.resources + ctx.files.absolute_resources),
        outputs = [outjar],
        mnemonic = "JarResources",
    )

    return [
        DefaultInfo(
            files = depset([outjar]),
        ),
        JavaInfo(
            compile_jar = outjar,
            output_jar = outjar,
        ),
    ]

_resource_jar = rule(
    implementation = _resource_jar_impl,
    attrs = {
        "absolute_resources": attr.label_keyed_string_dict(
            allow_files = True,
        ),
        "jar": attr.output(mandatory = True),
        "resources": attr.label_list(allow_files = True),
        "root": attr.string(mandatory = False, default = ""),
        "_zipper": attr.label(
            executable = True,
            cfg = "host",
            default = Label("@bazel_tools//tools/zip:zipper"),
            allow_files = True,
        ),
    },
)
