load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "messages",
    srcs = glob(["*.java"]),
    visibility = ["//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi/data",
    ],
)
