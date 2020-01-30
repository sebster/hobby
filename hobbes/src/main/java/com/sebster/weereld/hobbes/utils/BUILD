load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "utils",
    srcs = glob(["*.java"]),
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:org_slf4j_slf4j_api",
    ],
)
