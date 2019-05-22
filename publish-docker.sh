#!/usr/bin/env bash

DOCKER_USER=bluefu
DOCKER_IMAGE=service-tfs
DOCKER_IMAGE_TAG=latest
DOCKER_FULL_TAG="$DOCKER_USER/$DOCKER_IMAGE:$DOCKER_IMAGE_TAG"

docker login -u "$DOCKER_USER" && docker build -f docker/Dockerfile --tag "$DOCKER_FULL_TAG" . && docker push "$DOCKER_FULL_TAG"