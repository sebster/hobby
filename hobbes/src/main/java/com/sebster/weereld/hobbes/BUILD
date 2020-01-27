load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//tools/rules_images:defs.bzl", "spring_java_image")

java_library(
    name = "properties",
    resource_strip_prefix = "hobbes/src/main/java/com/sebster/weereld/hobbes/",
    resources = [
        "application.properties",
        "application-dev.properties",
    ],
)

java_library(
    name = "hobbes",
    srcs = glob(["**/*.java"]),
    resource_jars = ["properties"],
    resources = [
        "db.changelog-master.xml",
        "people/demo-data.sql",
    ],
    runtime_deps = [
        "@maven//:com_h2database_h2",
        "@maven//:org_apache_httpcomponents_httpclient",
        "@maven//:org_postgresql_postgresql",
        "@maven//:org_springframework_boot_spring_boot_starter_json",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//telegram-api",
        "//telegram-impl",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:commons_io_commons_io",
        "@maven//:javax_persistence_javax_persistence_api",
        "@maven//:javax_transaction_javax_transaction_api",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_liquibase_liquibase_core",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_boot_spring_boot_starter_data_jpa",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_data_spring_data_jpa",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)

java_binary(
    name = "bin",
    main_class = "com.sebster.weereld.hobbes.Application",
    visibility = ["//visibility:public"],
    runtime_deps = [":hobbes"],
)

spring_java_image(
    name = "image",
    main_class = "com.sebster.weereld.hobbes.Application",
    visibility = ["//visibility:public"],
    runtime_deps = [":hobbes"],
)
