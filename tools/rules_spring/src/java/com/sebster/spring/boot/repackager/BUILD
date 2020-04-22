load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "repackager",
    srcs = glob(["*.java"]),
    main_class = "com.sebster.spring.boot.repackager.CommandLineRepackager",
    visibility = ["//visibility:public"],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "@maven//:info_picocli_picocli",
        "@maven//:org_springframework_boot_spring_boot_loader_tools",
    ],
)
