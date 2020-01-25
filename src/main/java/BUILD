load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_library(
    name = "hobbes",
    srcs = glob(["**/*.java"]),
    resources = [
        "application.properties",
        "application-dev.properties",
        "db/changelog/db.changelog-master.xml",
    ],
    runtime_deps = [
        "@maven//:com_h2database_h2",
        "@maven//:org_apache_httpcomponents_httpclient",
        "@maven//:org_springframework_boot_spring_boot_starter_json",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
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
    name = "hobbes_bin",
    main_class = "com.sebster.weereld.hobbes.Application",
    visibility = ["//visibility:public"],
    runtime_deps = [":hobbes"],
)
