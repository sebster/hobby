load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//tools/rules_images:defs.bzl", "java_image")
load("//tools/rules_resources:defs.bzl", "resource_jar")

resource_jar(
    name = "properties",
    resources = [
        "application.properties",
        "application-dev.properties",
    ],
)

java_library(
    name = "hobbes",
    srcs = glob(["*.java"]),
    plugins = [
        "//3rdparty/org/springframework/boot:spring_boot_configuration_processor_plugin",
    ],
    resource_jars = ["properties"],
    resources = [
        "db.changelog-master.xml",
    ],
    runtime_deps = [
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/birthday",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/earlybird",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/plato",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/plugininfo",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/tickers/bitcoin/coindesk",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/tickers/bitcoin/kraken",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/tickers/metals/bullionvault",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/tickers/rates/wuvwav",
        "//lib/commons/src/main/java/com/sebster/commons/clock/springboot",
        "//lib/telegram/botapi-impl",
        "@maven//:com_h2database_h2",
        "@maven//:org_apache_httpcomponents_httpclient",
        "@maven//:org_liquibase_liquibase_core",
        "@maven//:org_postgresql_postgresql",
        "@maven//:org_springframework_boot_spring_boot_starter_data_jpa",
        "@maven//:org_springframework_boot_spring_boot_starter_json",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
    ],
    deps = [
        "//3rdparty/org/projectlombok/lombok",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/plugins/api",
        "//hobbes/src/main/java/com/sebster/weereld/hobbes/utils",
        "//lib/telegram/botapi",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_web",
    ],
)

java_binary(
    name = "bin",
    main_class = "com.sebster.weereld.hobbes.Hobbes",
    visibility = ["//visibility:public"],
    runtime_deps = [":hobbes"],
)

java_image(
    name = "image",
    main_class = "com.sebster.weereld.hobbes.Hobbes",
    visibility = ["//visibility:public"],
    runtime_deps = [":hobbes"],
)
