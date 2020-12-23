#!/bin/bash
set -u
key=$1
message=$2
echo -n $message | openssl dgst -sha256 -hmac $key -binary | base64
