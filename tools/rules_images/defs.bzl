load("@io_bazel_rules_docker//container:image.bzl", "container_image")
load("@io_bazel_rules_docker//java:image.bzl", _java_image = "java_image")

BASE_IMAGE = "//tools/rules_images:java_base_image"

def java_image(
        name,
        main_class,
        runtime_deps,
        visibility = None,
        container_layers = None,
        **kwargs):
    """Allow for runtime adjustments of config and classpath by using Spring's PropertiesLauncher.

    In the resulting docker image:
    - config can be put in /app/config/
    - additional libraries can be added to the classpath by putting them in /app/libs/

    This macro also allows adding packages to the image using container_image's debs facility.
    """

    if container_layers != None:
        container_image(
            name = "%s_base" % name,
            base = BASE_IMAGE,
            layers = container_layers,
        )

    _java_image(
        name = name,
        base = BASE_IMAGE if container_layers == None else ":%s_base" % name,
        runtime_deps = runtime_deps + ["@maven//:org_springframework_boot_spring_boot_loader"],
        main_class = "org.springframework.boot.loader.PropertiesLauncher",
        jvm_flags = ["-Dloader.main=" + main_class, "-Dloader.path=/app/libs"],
        visibility = visibility,
        **kwargs
    )
