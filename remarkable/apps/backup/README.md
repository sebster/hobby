# reMarkable Backup

This is a tool used to backup the contents of one or more reMarkables
(https://www.remarkable.com) using their cloud API.

This is completley unofficial API use, so no guarantees, and don't bug
reMarkable support about it if stuff breaks.

## Running the Backup Application

To run the backup app you need to register a client and get a login token.
Use the [Remarkable Cli](../cli) tool for that. The registration will save
information about the client to a `.remarkable-clients.json` file in your
home directory.

The following properties control the backup:

```properties
remarkable.clients=/the/path/to/the/clients.json
remarkable.backup.location=/the/path/to/the/backup/directory
remarkable.backup.client-id=the-optional-client-uuid
remarkable.backup.type=full
```

By default when running the backup it will read the clients file and
do a backup of all listed clients. If the `remarkable.backup.client-id`
property is set, it will only do a backup of that client. Clients can
also be selected by giving them as command line arguments, where the
argument may be the client number (e.g, 1, 2, ...), the start of the
client id (e.g., 34a6), or part of the client description (e.g.,
"work", "home").

The`remarkable.backup.location` property specifies the root directory of
the backup. A subfolder will be created for each client.

The `remarkable.backup.type` property can either be `full` or `incremental`.
By default the backup is incremental. In full mode all files are
re-downloaded.

#### Running from the Command Line

To run the backup app using bazel, run the following command:

```shell script
bazel run //remarkable/apps/backup
```

If you want to build a self-contained runnable jar file to run instead,
use the following bazel command:

```shell script
bazel build //remarkable/apps/backup:jar
```

Then take the created artifact and copy it wherever you want. It can be run
with `java -jar remarkable-backup.jar`.

#### Running in IntelliJ

Note that in IntelliJ it's useful to use the long (non-aliased) java_binary
rule target `//remarkable/apps/backup/src/main/java/com/sebster/remarkable/backup:bin`
to run the backup app because then IntelliJ will understand it's a Java binary and
will also allow running it in debug mode.
