load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "plato",
    srcs = glob(["*.java"]),
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//lib/telegram/botapi",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:commons_io_commons_io",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)
