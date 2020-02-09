load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "specifications",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:org_apache_commons_commons_lang3",
    ],
)
