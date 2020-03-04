load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "PlatoEntryServiceTest",
    size = "small",
    srcs = glob(["*.java"]),
    resources = glob([
        "*.html",
        "*.txt",
    ]),
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/plato",
        "@maven//:commons_io_commons_io",
        "@maven//:junit_junit",
        "@maven//:org_hamcrest_hamcrest",
    ],
)
