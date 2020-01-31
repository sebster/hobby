load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "api",
    srcs = glob(["*.java"]),
    visibility = ["//hobbes/src/main/java/com/sebster/weereld/hobbes:__subpackages__"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/people",
        "//lib/telegram/telegram-api",
        "@maven//:javax_transaction_javax_transaction_api",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_spring_beans",
    ],
)
