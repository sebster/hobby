load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "cloudapi",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/collections",
        "//lib/commons/src/main/java/com/sebster/commons/io",
        "//lib/commons/src/main/java/com/sebster/commons/strings",
    ],
)
