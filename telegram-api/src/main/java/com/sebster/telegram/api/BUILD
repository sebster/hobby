load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "api",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "//telegram-api/src/main/java/com/sebster/telegram/api/data",
        "//telegram-api/src/main/java/com/sebster/telegram/api/messages",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//telegram-api/src/main/java/com/sebster/telegram/api/data",
        "//telegram-api/src/main/java/com/sebster/telegram/api/messages",
    ],
)
