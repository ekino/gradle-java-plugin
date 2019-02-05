#!/usr/bin/env sh

if [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ];
then
    openssl aes-256-cbc -K $encrypted_7497f1146585_key -iv $encrypted_7497f1146585_iv -in my.travis.gpg.enc -out my.travis.gpg -d
fi
