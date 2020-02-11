load("@rules_java//java:defs.bzl", "java_library")
load("//tools/rules_resources:defs.bzl", "resource_jar")

java_library(
    name = "orders",
    srcs = glob(["*.java"]),
    resource_jars = [":spring_autoconfig"],
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/repository/api",
        "//lib/repository/jpa/src/main/java/com/sebster/repository/jpa/orders",
        "//lib/repository/jpa/src/main/java/com/sebster/repository/jpa/properties",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
    ],
)

resource_jar(
    name = "spring_autoconfig",
    resources = ["spring.factories"],
    root = "META-INF",
)
