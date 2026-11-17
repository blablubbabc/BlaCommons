#!/usr/bin/env bash
if [ $# -eq 0 ]; then
    echo "Missing argument(s)"
    exit 1
fi

# Check for predefined JDK version aliases:
case "$1" in
  8)
    JDK_VERSION="8.0.412-tem"
    ;;
  17)
    JDK_VERSION="17.0.11-tem"
    ;;
  21)
    JDK_VERSION="21.0.3-tem"
    ;;
  25)
    JDK_VERSION="25.0.2-tem"
    ;;
  *)
    JDK_VERSION=$1
    ;;
esac

echo Installing Java JDK: $JDK_VERSION

# Use Sdkman to manage multiple JDK versions regardless of the platform.
# Workaround for zip missing on some platforms (e.g. Git bash for Windows):
if [ ! -f ~/bin/zip ]; then
  echo "Installing unzip -> zip"
  mkdir -p ~/bin
  cp /usr/bin/unzip ~/bin/zip
fi

# Install Sdkman if it is not already installed:
curl -s "https://get.sdkman.io?rcupdate=false" | bash
source "${HOME}/.sdkman/bin/sdkman-init.sh"

# Update if an update is available:
sdk selfupdate

# Install the requested JDK version:
sdk install java $JDK_VERSION

sdk use java $JDK_VERSION

# Verify that the expected Java version is used:
#echo Java home: $JAVA_HOME
#echo Path: $PATH
echo Active Java version: $(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
