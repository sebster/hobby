load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "domain",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/apps/backup/src/main/java/com/sebster/remarkable/backup:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/lib/cloudapi",
        "@maven//:org_slf4j_slf4j_api",
    ],
)
