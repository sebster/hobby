#!/bin/bash

# Find the client.
REMARKABLE_CLIENT_ID="$1"
if [ ! "$REMARKABLE_CLIENT_ID" ]; then
  echo "Usage: source $0 <clientId>"
  return 1
fi
export REMARKABLE_CLIENT_ID

unset REMARKABLE_LOGIN_TOKEN
unset REMARKABLE_SESSION_TOKEN
unset REMARKABLE_STORAGE_HOST

# Find the login token for the specfied client.
REMARKABLE_LOGIN_TOKEN=$(jq -r ".[] | select(.clientId == \"$REMARKABLE_CLIENT_ID\") | .loginToken" ~/.remarkable-clients.json)
if [ ! "$REMARKABLE_LOGIN_TOKEN" ]; then
  echo "No authentication token for client: $REMARKABLE_CLIENT_ID"
  return 1
fi
export REMARKABLE_LOGIN_TOKEN
echo REMARKABLE_LOGIN_TOKEN="$REMARKABLE_LOGIN_TOKEN"

# Login to get a session token.
REMARKABLE_SESSION_TOKEN=$(
  http POST https://my.remarkable.com/token/json/2/user/new Authorization:"Bearer $REMARKABLE_LOGIN_TOKEN"
)
if [ ! "$REMARKABLE_SESSION_TOKEN" ]; then
  echo "Login failed."
  return 1
fi
export REMARKABLE_SESSION_TOKEN
echo REMARKABLE_SESSION_TOKEN="$REMARKABLE_SESSION_TOKEN"

# Get storage host URL.
REMARKABLE_STORAGE_HOST=$(
  http POST https://service-manager-production-dot-remarkable-production.appspot.com/service/json/1/document-storage \
    environment==production group==auth0%7C5a68dc51cb30df3877a1d7c4 apiVer==2 \
    Authorization:"Bearer $REMARKABLE_SESSION_TOKEN" | jq -r .Host
)
if [ ! "$REMARKABLE_STORAGE_HOST" ]; then
  echo "Cannot find storage host."
  return 1
fi
export REMARKABLE_STORAGE_HOST
echo REMARKABLE_STORAGE_HOST="$REMARKABLE_STORAGE_HOST"
