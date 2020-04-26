# reMarkable Tools

This directory contains shell scripts to play with the API. They're not
much more than wrappers around [HTTPie](https://httpie.org/).

The scripts require that some environment variables are set using the
`login.sh` script:

```shell script
source ./login.sh <clientId>
```

The client id is the UUID contained in the `$HOME/.remarkable-clients.json`
file, which is created by registering a client with the
[Remarkable CLI](../apps/cli).

