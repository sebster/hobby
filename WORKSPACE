workspace(
    name = "hobbes",
)

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_java",
    sha256 = "220b87d8cfabd22d1c6d8e3cdb4249abd4c93dcc152e0667db061fb1b957ee68",
    url = "https://github.com/bazelbuild/rules_java/releases/download/0.1.1/rules_java-0.1.1.tar.gz",
)

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")

rules_java_dependencies()

rules_java_toolchains()

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.0"

RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    artifacts = [
        "ch.qos.logback:logback-classic:1.2.3",
        "com.h2database:h2:1.4.200",
        "commons-io:commons-io:2.6",
        "io.rest-assured:rest-assured:3.3.0",
        "javax.annotation:javax.annotation-api:1.3.2",
        "javax.servlet:javax.servlet-api:4.0.1",
        "javax.validation:validation-api:2.0.1.Final",
        "javax.transaction:javax.transaction-api:1.3",
        "junit:junit:4.12",
        "org.apache.commons:commons-lang3:3.9",
        "org.bouncycastle:bcpkix-jdk15on:1.55",
        "org.hamcrest:hamcrest:2.1",
        "org.hibernate:hibernate-jpamodelgen:5.4.10.Final",
        "javax.persistence:javax.persistence-api:2.2",
        "org.liquibase:liquibase-core:3.8.5",
        "org.postgresql:postgresql:42.2.9",
        "org.projectlombok:lombok:1.18.10",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.slf4j:slf4j-api:1.7.30",
        "org.springframework:spring-context:5.2.3.RELEASE",
        "org.springframework:spring-core:5.2.3.RELEASE",
        "org.springframework:spring-jdbc:5.2.3.RELEASE",
        "org.springframework:spring-orm:5.2.3.RELEASE",
        "org.springframework:spring-tx:5.2.3.RELEASE",
        "org.springframework.boot:spring-boot-devtools:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-loader:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-actuator:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-web:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-data-jpa:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-jdbc:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-json:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-security:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-test:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-tomcat:2.2.4.RELEASE",
        "org.springframework.boot:spring-boot-starter-web:2.2.4.RELEASE",
        "org.apache.httpcomponents:httpclient:4.5.10",
        maven.artifact(
            group = "org.springframework.boot",
            artifact = "spring-boot-configuration-processor",
            version = "2.2.4.RELEASE",
            neverlink = True,
        ),
        "org.springframework.data:spring-data-commons:2.2.4.RELEASE",
    ],
    fetch_sources = True,
    maven_install_json = "//:maven_install.json",
    repositories = [
        "https://repo.maven.apache.org/maven2",
    ],
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
