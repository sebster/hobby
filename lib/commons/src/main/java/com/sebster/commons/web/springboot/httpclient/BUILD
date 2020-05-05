load("@rules_java//java:defs.bzl", "java_library")
load("//tools/rules_resources:defs.bzl", "resource_jar")

java_library(
    name = "httpclient",
    srcs = glob(["*.java"]),
    resource_jars = [":spring_autoconfig"],
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:org_apache_httpcomponents_httpclient",
        "@maven//:org_apache_httpcomponents_httpcore",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)

resource_jar(
    name = "spring_autoconfig",
    resources = ["spring.factories"],
    root = "META-INF",
)
