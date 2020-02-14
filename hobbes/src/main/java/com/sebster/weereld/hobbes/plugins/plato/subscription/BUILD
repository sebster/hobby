load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "subscription",
    srcs = glob(["*.java"]),
    plugins = ["//3rdparty/org/hibernate:hibernate_jpamodelgen_plugin"],
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/plato:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/repository/api/src/main/java/com/sebster/repository/api",
        "//lib/repository/api/src/main/java/com/sebster/repository/api/properties",
        "//lib/repository/api/src/main/java/com/sebster/repository/api/properties/specifications",
        "//lib/repository/api/src/main/java/com/sebster/repository/api/specifications",
        "//lib/repository/jpa/src/main/java/com/sebster/repository/jpa",
        "//lib/repository/jpa/src/main/java/com/sebster/repository/jpa/properties",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_tx",
    ],
)
