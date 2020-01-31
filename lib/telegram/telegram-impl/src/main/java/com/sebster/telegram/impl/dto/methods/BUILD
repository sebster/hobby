load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "methods",
    srcs = glob(["**/*.java"]),
    visibility = ["//lib/telegram/telegram-impl/src/main/java/com/sebster/telegram/impl:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/telegram/telegram-api",
        "//lib/telegram/telegram-impl/src/main/java/com/sebster/telegram/impl/dto/data",
    ],
)
