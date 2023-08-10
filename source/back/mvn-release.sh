#!/bin/bash

if [ $# -ne 2 ]
then
  echo "Use ${0} VERSION NEW_VERSION"
  echo "  Ex: ${0} 1.0.0 1.0.1-SNAPSHOT"
  exit 127
fi

VERSION=$1
NEW_VERSION=$2
CHANGE_VERSION=1

if [ "$VERSION" == "$NEW_VERSION" ]
then
  CHANGE_VERSION=0
fi

echo "Generating version $VERSION"
if [ $CHANGE_VERSION -eq 1 ]
then
  echo "New Version $NEW_VERSION"
fi

GIT_TAG="v${VERSION}"
export JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"

mvn clean

if [ $CHANGE_VERSION -eq 1 ]
then
  mvn versions:set -DnewVersion=${VERSION}
fi

mvn -DskipTests=true package

if [ $? -eq 0 ]
then
  mvn docker:build docker:push
  if [ $? -eq 0 -a $CHANGE_VERSION -eq 1 ]
  then
    mvn versions:commit
    git tag -f $GIT_TAG
    git push -f origin $GIT_TAG
    mvn versions:set -DnewVersion=${NEW_VERSION}
    mvn versions:commit
    git commit -a -m "New snapshot"
    git push origin master
  elif [ $CHANGE_VERSION -eq 1 ]
  then
    mvn versions:revert 
  fi
elif [ $CHANGE_VERSION -eq 1 ]
then
  mvn versions:revert
fi

