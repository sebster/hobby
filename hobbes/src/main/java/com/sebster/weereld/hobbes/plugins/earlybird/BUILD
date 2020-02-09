load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "earlybird",
    srcs = glob(["*.java"]),
    plugins = ["//3rdparty/org/hibernate:hibernate_jpamodelgen_plugin"],
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/people",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/utils",
        "//lib/repository/api",
        "//lib/repository/jpa",
        "//lib/telegram/botapi",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_data_spring_data_jpa",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
    ],
)
