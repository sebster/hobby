load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_library(
    name = "spring_boot_configuration_processor",
    exported_plugins = [
        ":spring_boot_configuration_processor_plugin",
    ],
    neverlink = True,
    visibility = [
        "//visibility:public",
    ],
)

java_plugin(
    name = "spring_boot_configuration_processor_plugin",
    processor_class = "org.springframework.boot.configurationprocessor.ConfigurationMetadataAnnotationProcessor",
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "@maven//:org_springframework_boot_spring_boot_configuration_processor",
    ],
)
