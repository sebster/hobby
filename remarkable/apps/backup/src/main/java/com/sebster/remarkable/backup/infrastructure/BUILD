load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "infrastructure",
    srcs = glob(["*.java"]),
    visibility = ["//remarkable/apps/backup/src/main/java/com/sebster/remarkable/backup:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//remarkable/apps/backup/src/main/java/com/sebster/remarkable/backup/domain",
        "//remarkable/lib/cloudapi",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:commons_io_commons_io",
        "@maven//:javax_annotation_javax_annotation_api",
        "@maven//:org_slf4j_slf4j_api",
    ],
)
