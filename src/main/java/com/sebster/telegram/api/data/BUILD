load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "data",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/com/sebster/telegram/api:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:org_apache_commons_commons_lang3",
    ],
)
