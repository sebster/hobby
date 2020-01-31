load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "api",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "//lib/telegram/telegram-api/src/main/java/com/sebster/telegram/api/data",
        "//lib/telegram/telegram-api/src/main/java/com/sebster/telegram/api/messages",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/telegram/telegram-api/src/main/java/com/sebster/telegram/api/data",
        "//lib/telegram/telegram-api/src/main/java/com/sebster/telegram/api/messages",
    ],
)
