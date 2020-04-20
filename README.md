# Hobby

This repository contains some of my hobby projects.

Currently this includes:

-   A repository API for querying and persisting domain objects.
-   Telegram client for the Bot API (https://core.telegram.org/bots/api).
-   A silly telegram bot with plugins to do all kinds of silly stuff.

## Repository Structure

-   /lib - useful generic libraries
    -   /repository - repository API
        -   /repository/api - repository API for use in domain classes
        -   /repository/mem - in memory repository API implementation, for use in domain logic tests
        -   /repository/jpa - JPA based repository API implementation
    -   /telegram - telegram Bot API
        -   /telegram/botapi - telegram Bot API client
        -   /telegram/botapi-impl - implementation of the Telegram Bot API client
-   /hobbes - a silly telegram bot
-   /remarkable - tools for my reMarkable (https://www.remarkable.com)
    -   /lib - libraries for using the remarkable
        -   /lib/cloudapi - unofficial client for the reMarkable cloud storage API
        -   /lib/cloudapi-impl - implementation of the reMarkable cloud storage API client
    -   /apps - applications for the reMarkable
        -   /apps/auto-import - watch a folder and automatically import documents to the reMarkable (TODO)
        -   /apps/auto-mirror - watch two reMarkables and make sure they have identical contents (TODO)
        -   /apps/backup - make a backup of the reMarkable to the local file system
        -   /apps/restore - restore files from a backup to the reMarkable (TODO)
-   /3rd-party - bazel rules for 3rd party libraries
-   /tools - custom bazel rules for spring, docker images, etc.

## How to Build

### Prerequisites

This project uses Google's [bazel](https://bazel.build/) build system to build
all the artifacts (jars, docker images, etc.).

To run bazel, it's useful to use [bazelisk](https://github.com/bazelbuild/bazelisk)
which is a user friendly launcher for bazel:

> Bazelisk is a wrapper for Bazel written in Go. It automatically picks a
> good version of Bazel given your current working directory, downloads it
> from the official server (if required) and then transparently passes
> through all command-line arguments to the real Bazel binary.

To install bazelisk make sure you have a recent version of go (at least version 1.1) and run:

```shell script
go get github.com/bazelbuild/bazelisk
```

You can add it to your `PATH` using:

```shell script
export PATH=$PATH:$(go env GOPATH)/bin
```

It's also useful to add a symlink to your path so you can simply invoke
bazelisk as `bazel`. For example, if your `PATH` includes `$HOME/bin`:

```shell script
ln -s $(go env GOPATH)/bin/bazelisk $HOME/bin/bazel
```

### Cloning the Repository

Next you need to get the project source code.

To clone the repository, issue the following command:

```shell script
git clone git@github.com:sebster/hobby.git
```

Then change into the project workspace root directory:

```shell script
cd hobby
```

### Building the Code

To build all repository targets use the `//...` target to `bazel`:

```shell script
bazel build //...
```

To run all tests:

```shell script
bazel test //...
```

### Running Hobbes, the silly Telegram Bot

For instructions on how to run and deploy Hobbes, see
the [README](hobbes/README.md) file for Hobbes.

## Importing the Project in IntelliJ IDEA

To import the project in IntelliJ:

-   first install the Bazel plugin
-   make sure you have JDK 11 available
-   install the Lombok plugin
-   import the project using the defaults
