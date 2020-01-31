load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "kraken",
    srcs = glob(["*.java"]),
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//lib/telegram/botapi",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)
