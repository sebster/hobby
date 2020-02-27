load("@rules_java//java:defs.bzl", "java_library")
load("//tools/rules_resources:defs.bzl", "resource_jar")

java_library(
    name = "springboot",
    srcs = glob(["*.java"]),
    resource_jars = [":spring_autoconfig"],
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/clock",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_spring_context",
    ],
)

resource_jar(
    name = "spring_autoconfig",
    resources = ["spring.factories"],
    root = "META-INF",
)
