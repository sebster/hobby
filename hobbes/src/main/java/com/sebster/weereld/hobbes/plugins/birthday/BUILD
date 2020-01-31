load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "birthday",
    srcs = glob(["*.java"]),
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/people",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/utils",
        "//lib/telegram/botapi",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
    ],
)
