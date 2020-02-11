load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

DIGESTS = {
    "java-11": "sha256:0ce06c40e99e0dce26bdbcec30afe7a890a57bbd250777bd31ff2d1b798c7809",
    "java-11-debug": "sha256:e91e23383a8843a3f0bb00bdf99b9b7bed8c7ce25ed929acbe0eedec70fa91f9",
}

DEFAULT_JAVA_BASE = select({
    "@io_bazel_rules_docker//:debug": "@distroless-java-11-debug//image",
    "@io_bazel_rules_docker//:fastbuild": "@distroless-java-11//image",
    "@io_bazel_rules_docker//:optimized": "@distroless-java-11//image",
    "//conditions:default": "@distroless-java-11//image",
})

def pull_distroless_java_base_images():
    container_pull(
        name = "distroless-java-11",
        digest = DIGESTS["java-11"],
        registry = "gcr.io",
        repository = "distroless/java",
    )

    container_pull(
        name = "distroless-java-11-debug",
        digest = DIGESTS["java-11-debug"],
        registry = "gcr.io",
        repository = "distroless/java",
    )
