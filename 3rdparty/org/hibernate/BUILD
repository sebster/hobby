load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

java_library(
    name = "hibernate_jpamodelgen",
    exported_plugins = [
        ":hibernate_jpamodelgen_plugin",
    ],
    visibility = [
        "//visibility:public",
    ],
    exports = [
        "@maven//:org_hibernate_hibernate_jpamodelgen",
    ],
)

java_plugin(
    name = "hibernate_jpamodelgen_plugin",
    generates_api = True,
    processor_class = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor",
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "@maven//:org_hibernate_hibernate_jpamodelgen",
    ],
)
