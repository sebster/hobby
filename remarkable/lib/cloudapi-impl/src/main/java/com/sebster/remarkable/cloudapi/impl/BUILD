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
    runtime_deps = [
        "@maven//:org_springframework_boot_spring_boot_starter_validation",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/lib/cloudapi",
        "//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl/controller",
        "//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl/infrastructure/http",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:javax_validation_validation_api",
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
