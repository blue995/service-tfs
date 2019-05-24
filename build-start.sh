#!/usr/bin/env bash

DIR="build/libs"
SERVICE="service-delegation-4.3.0-SNAPSHOT"
BASEDIR=$(dirname "$0")
$BASEDIR/build.sh


java -jar "$BASEDIR/$DIR/$SERVICE.jar"
