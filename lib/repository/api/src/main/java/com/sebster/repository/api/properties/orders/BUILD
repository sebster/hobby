load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "orders",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/repository/api/src/main/java/com/sebster/repository/api/orders",
        "//lib/repository/api/src/main/java/com/sebster/repository/api/properties",
    ],
)
