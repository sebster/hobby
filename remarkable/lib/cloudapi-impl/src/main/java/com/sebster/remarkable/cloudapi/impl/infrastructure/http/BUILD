load("@rules_java//java:defs.bzl", "java_library")
load("//tools/rules_resources:defs.bzl", "resource_jar")

java_library(
    name = "http",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl/controller",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_core",
        "@maven//:org_springframework_spring_web",
    ],
)
