load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "methods",
    srcs = glob(["**/*.java"]),
    visibility = ["//telegram-impl/src/main/java/com/sebster/telegram/impl:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//telegram-api",
        "//telegram-impl/src/main/java/com/sebster/telegram/impl/dto/data",
    ],
)
