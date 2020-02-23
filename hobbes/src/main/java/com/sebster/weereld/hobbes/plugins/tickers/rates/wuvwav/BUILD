load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "wuvwav",
    srcs = glob(["*.java"]),
    plugins = [
        "//3rdparty/org/springframework/boot:spring_boot_configuration_processor_plugin",
    ],
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//lib/telegram/botapi",
        "@maven//:commons_io_commons_io",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
    ],
)
