load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "commands",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli:__pkg__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/uuids",
        "//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli/commands/completion",
        "//remarkable/lib/cloudapi",
        "@maven//:commons_io_commons_io",
        "@maven//:info_picocli_picocli",
        "@maven//:org_jline_jline",
    ],
)
