load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "completion",
    srcs = glob(["*.java"]),
    visibility = [
        "//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli:__pkg__",
        "//remarkable/apps/cli/src/main/java/com/sebster/remarkable/cli/commands:__pkg__",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/commons/src/main/java/com/sebster/commons/strings",
        "//remarkable/lib/cloudapi",
        "@maven//:info_picocli_picocli",
        "@maven//:org_jline_jline",
    ],
)
