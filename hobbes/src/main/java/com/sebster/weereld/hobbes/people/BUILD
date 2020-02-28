load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "people",
    srcs = glob(["*.java"]),
    plugins = ["//3rdparty/org/hibernate:hibernate_jpamodelgen_plugin"],
    resources = ["demo-data.sql"],
    visibility = [
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins:__subpackages__",
        "//hobbes/src/test/java/com/sebster/weereld/hobbes/plugins:__subpackages__",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/functions",
        "//lib/repository/api",
        "//lib/repository/jpa",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_spring_context",
    ],
)
