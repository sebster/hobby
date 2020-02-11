load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "InMemoryRepositoryTest",
    size = "small",
    srcs = glob(["InMemoryRepositoryTest.java"]),
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//lib/repository/api",
        "//lib/repository/api/src/test/java/com/sebster/repository/api",
        "//lib/repository/mem",
        "@maven//:junit_junit",
    ],
)
