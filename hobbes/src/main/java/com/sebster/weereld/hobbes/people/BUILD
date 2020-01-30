load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "people",
    srcs = glob(["*.java"]),
    resources = ["demo-data.sql"],
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_spring_context",
    ],
)
