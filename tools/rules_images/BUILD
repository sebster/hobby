load("@io_bazel_rules_docker//contrib:passwd.bzl", "passwd_entry", "passwd_tar")
load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@io_bazel_rules_docker//container:image.bzl", "container_image")
load("//tools/rules_images:distroless.bzl", "DEFAULT_JAVA_BASE")

passwd_entry(
    name = "root_user",
    gid = 0,
    uid = 0,
    username = "root",
)

passwd_entry(
    name = "nonroot_user",
    gid = 65532,
    info = "Leeroy Jenkins. At least he's got chicken. But no root.",
    uid = 65532,
    username = "leeroy",
)

passwd_tar(
    name = "passwd",
    entries = [
        ":root_user",
        ":nonroot_user",
    ],
    passwd_file_pkg_dir = "etc",
)

container_image(
    name = "java_base_image",
    base = DEFAULT_JAVA_BASE,
    tars = ["//tools/rules_images:passwd"],
    user = "leeroy",
    visibility = ["//visibility:public"],
)
