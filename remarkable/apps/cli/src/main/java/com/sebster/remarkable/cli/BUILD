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
        "//remarkable/lib/cloudapi-impl",
        "@maven//:org_apache_httpcomponents_httpclient",
        "@maven//:org_springframework_boot_spring_boot_starter_json",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli/commands",
        "//remarkable/lib/cloudapi",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:commons_io_commons_io",
        "@maven//:info_picocli_picocli",
        "@maven//:info_picocli_picocli_shell_jline3",
        "@maven//:org_fusesource_jansi_jansi",
        "@maven//:org_jline_jline",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_spring_context",
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
    main_class = "com.sebster.remarkable.cli.RemarkableClie",
    visibility = ["//visibility:public"],
)

alias(
    name = "cli",
    actual = ":bin",
    visibility = ["//visibility:public"],
)