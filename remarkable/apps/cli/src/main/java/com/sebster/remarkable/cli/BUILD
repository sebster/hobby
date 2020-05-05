load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//tools/rules_images:defs.bzl", "java_image")
load("//tools/rules_resources:defs.bzl", "resource_jar")
load("//tools/rules_spring:defs.bzl", "spring_boot_jar")

resource_jar(
    name = "properties",
    resources = [
        "application.properties",
    ],
)

java_library(
    name = "app",
    srcs = glob(["*.java"]),
    plugins = [
        "//3rdparty/org/springframework/boot:spring_boot_configuration_processor_plugin",
    ],
    resource_jars = ["properties"],
    runtime_deps = [
        "//lib/commons/src/main/java/com/sebster/commons/web/springboot/httpclient",
        "//remarkable/lib/cloudapi-impl",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/collections",
        "//lib/commons/src/main/java/com/sebster/commons/strings",
        "//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli/commands",
        "//remarkable/lib/cloudapi",
        "@maven//:info_picocli_picocli",
        "@maven//:info_picocli_picocli_shell_jline3",
        "@maven//:org_jline_jline",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
    ],
)

java_binary(
    name = "bin",
    main_class = "com.sebster.remarkable.cli.RemarkableCli",
    visibility = ["//visibility:public"],
    runtime_deps = [":app"],
)

java_image(
    name = "image",
    main_class = "com.sebster.remarkable.cli.RemarkableCli",
    visibility = ["//visibility:public"],
    runtime_deps = [":app"],
)

spring_boot_jar(
    name = "jar",
    app = ":app",
    jar = "remarkable-cli.jar",
    main_class = "com.sebster.remarkable.cli.RemarkableCli",
    visibility = ["//visibility:public"],
)

alias(
    name = "cli",
    actual = ":bin",
    visibility = ["//visibility:public"],
)
