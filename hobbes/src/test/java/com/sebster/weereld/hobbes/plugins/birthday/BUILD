load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "BirthdayPluginTest",
    size = "small",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/people",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/birthday",
        "//lib/commons/src/main/java/com/sebster/commons/clock",
        "//lib/repository/api",
        "//lib/repository/mem",
        "//lib/telegram/botapi",
        "//lib/telegram/botapi-test",
        "@maven//:junit_junit",
        "@maven//:org_hamcrest_hamcrest",
    ],
)
