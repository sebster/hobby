load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "controller",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/lib/cloudapi-impl/src/main/java/com/sebster/remarkable/cloudapi/impl:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/lib/cloudapi",
    ],
)
