#!/usr/bin/env bash
# Abort on error
set -e

pushd "$(dirname "$BASH_SOURCE")"
trap popd EXIT

# We require Java 21 to build:
source scripts/installJDK.sh 21

# Build via Gradle:
./gradlew cleanInstall
