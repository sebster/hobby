# #Weereld Telegram Bot

This is a Telegram bot which migrates the TU/e #weereld IRC channel bots to
Telegram. The bot is rather silly, poorly tested, and often breaks in
unexpected ways. That's all part of the fun.

It might also not be very politically correct. It's meant for laughs, so don't
take it too seriously.

## Running the Bot

### Development

To run the bot in development, it's first necessary to get a Bot API key from
telegram. This can be done by chatting to the Telegram `BotFather` bot to
create a new bot for testing purposes.

Start a chat with the `BotFather` bot. To see the commands available send the
`/help` command. To create a new bot use the `/newbot` command and follow the
instructions. The `BotFather` will give you an API token which you should set in
the application-dev.properties, for example:

```properties
telegram.auth-key=145592486:AAGslFC1hkV1enYeFsx2GfoZY3bkKfU9VeU
```

It's also useful to set the logging level to `DEBUG` to see the messages
that are received by the bot:

```properties
logging.level.com.sebster=DEBUG
```

**NOTE: By default Hobbes will ignore all messages.** If you want Hobbes to
respond directly to other people's chats, they need to be on the user
whitelist. If you add Hobbes to a group, it will only respond if the group's
chat id is on the chat whitelist. Use the log output of rejected messages to
obtain the relevant user and chat ids, and add them to the properties file,
e.g.:

```properties
hobbes.telegram-from-white-list=89234640
hobbes.telegram-chat-white-list=-178399338,29838444
```

#### Running from the Command Line

To start Hobbes using bazel with the development profile enabled, run the
following command:

```shell script
bazel run //hobbes -- --spring.profiles.active=dev
```

#### Running in IntelliJ

Note that in IntelliJ it's useful to use the long (non-aliased) java_binary
rule target `//hobbes/src/main/java/com/sebster/weereld/hobbes:bin` to
run Hobbes because then IntelliJ will understand it's a Java binary and
will also allow running it in debug mode.

#### Database

Some plugins use a database for persistence. In development H2 is used and by
default Hobbes will start with the H2 console enabled. The H2 console is
located at [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

To log into the H2 console use the database URL `jdbc:h2:mem:testdb`, the
username `sa`, and a blank password.

Use this to see the database structure, execute queries, and insert/update data.

### Running in Production

To run the bot in production, first create a docker image:

```shell script
bazel run //hobbes:image -- --norun
```

The image can then be deployed for production purposes using Docker or
Kubernetes. The image uses the following mount points for volumes:

-   /app/config - put your production application.properties here.
-   /app/lib - put your production database JDBC driver jar here if you're
    using a database different from PostgreSQL.

Note: you can also just take the output of the java_binary rule target
`//hobbes/src/main/java/com/sebster/weereld/hobbes:bin` if you don't like
deploying images and would like to deploy the binary directly.
