#!/usr/bin/env bash

BASEDIR=$(dirname "$0")
export GRADLE_OPTS="$JAVA_OPTS -Dhttp.proxyHost=$($BASEDIR/get-proxy-host) -Dhttp.proxyPort=$($BASEDIR/get-proxy-port) -Dhttps.proxyHost=$($BASEDIR/get-proxy-host) -Dhttps.proxyPort=$($BASEDIR/get-proxy-port)"
$BASEDIR/gradlew build
