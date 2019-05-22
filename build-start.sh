#!/usr/bin/env bash

DIR="build/libs"
SERVICE="service-delegation-4.3.0-SNAPSHOT"

./build.sh

java -jar "$DIR/$SERVICE.jar"