#!/bin/bash

if [ ! "$REMARKABLE_SESSION_TOKEN" ]; then
  echo "Not logged in."
  exit 1
fi

if [ ! "$1" ] || [ ! "$2" ]; then
  echo "Usage: $0 <itemId> <itemVersion>"
  exit 1
fi

ITEM_ID="$1"
ITEM_VERSION="$2"

echo "[{ \"ID\": \"$ITEM_ID\", \"Version\": $ITEM_VERSION }]" |
  http --json -v PUT https://"$REMARKABLE_STORAGE_HOST"/document-storage/json/2/upload/request \
    Authorization:"Bearer $REMARKABLE_SESSION_TOKEN"
