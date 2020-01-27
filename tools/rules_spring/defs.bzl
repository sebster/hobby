load(":resources.bzl", _resource_jar = "resource_jar")

def resource_jar(name, jar = None, **kwargs):
    _resource_jar(
        name = name,
        jar = jar or "%s.jar" % name,
        **kwargs
    )
