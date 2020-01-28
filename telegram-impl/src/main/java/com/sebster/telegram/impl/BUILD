load("@rules_java//java:defs.bzl", "java_library")
load("//tools/rules_resources:defs.bzl", "resource_jar")

java_library(
    name = "impl",
    srcs = glob(["*.java"]),
    plugins = [
        "//3rdparty/org/springframework/boot:spring_boot_configuration_processor_plugin",
    ],
    resource_jars = [":spring_autoconfig"],
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//telegram-api",
        "//telegram-impl/src/main/java/com/sebster/telegram/impl/dto/data",
        "//telegram-impl/src/main/java/com/sebster/telegram/impl/dto/methods",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:javax_validation_validation_api",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)

resource_jar(
    name = "spring_autoconfig",
    resources = ["spring.factories"],
    root = "META-INF",
)
