load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "botapi",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    exports = [
        "//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi/data",
        "//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi/messages",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi/data",
        "//lib/telegram/botapi/src/main/java/com/sebster/telegram/botapi/messages",
    ],
)
