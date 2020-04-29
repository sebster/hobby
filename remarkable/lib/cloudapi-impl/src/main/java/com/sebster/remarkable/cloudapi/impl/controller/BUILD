load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "controller",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/collections",
        "//lib/commons/src/main/java/com/sebster/commons/functions",
        "//lib/commons/src/main/java/com/sebster/commons/strings",
        "//remarkable/lib/cloudapi",
    ],
)
