#!/usr/bin/env sh

chmod u+x gradlew

if [ "$TRAVIS_PULL_REQUEST" != "false" ];
then
    echo "Skip integration tests in pull request builds"
    ./gradlew clean build -x integrationTest -PossrhUsername=${SONATYPE_USERNAME} -PossrhPassword=${SONATYPE_PASSWORD}
else
    ./gradlew clean build -PossrhUsername=${SONATYPE_USERNAME} -PossrhPassword=${SONATYPE_PASSWORD}
fi
