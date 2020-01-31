load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "data",
    srcs = glob(["*.java"]),
    visibility = ["//lib/telegram/botapi-impl/src/main/java/com/sebster/telegram/botapi/impl:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/telegram/botapi",
    ],
)
