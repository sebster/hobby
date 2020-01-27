load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "impl",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//src/main/java/com/sebster/telegram/api",
        "//src/main/java/com/sebster/telegram/impl/dto/data",
        "//src/main/java/com/sebster/telegram/impl/dto/methods",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_springframework_spring_web",
    ],
)
