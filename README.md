# gradle java plugin

Java gradle plugin for Ekino projects

[![Build Status](https://travis-ci.org/ekino/gradle-java-plugin.svg?branch=master)](https://travis-ci.org/ekino/gradle-java-plugin)
[![GitHub (pre-)release](https://img.shields.io/github/release/ekino/gradle-java-plugin.svg)](https://github.com/ekino/gradle-java-plugin/releases)
[![GitHub license](https://img.shields.io/github/license/ekino/gradle-java-plugin.svg)](https://github.com/ekino/gradle-java-plugin/blob/master/LICENSE.md)

## Overview

This plugin configures the following tasks for any Ekino Java project :

* Apply the [Gradle Java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
* Set the source/target compatibility to `1.11` java version
* Define the `integrationTest` source set (`it` directory) : [unbroken-dome/gradle-testsets-plugin](https://github.com/unbroken-dome/gradle-testsets-plugin)
* Define the test report files aggregation
* Replace `${project-version}` and `${project-description}` in YAML files by the gradle project version
* Define JUnit Platform configuration for JUnit 5 (Jupiter)

## Requirement

You need to have a JDK 8 at least.

Nota Bene : some build variables may cause error for launching the gradle command.
You have to add a `gradle.properties` file to the `~/.gradle` folder under your home directory with following configuration : 

    publishingBaseUrl=<NEXUS_BASE_URL>
    publishingLogin=<NEXUS_LOGIN>
    publishingPassword=<NEXUS_PASSWORD>

<NEXUS_BASE_URL> is the Nexus prefix URL without the repository name, finishing with a dash '/'.

## Build

This will create the JAR and run the tests

    ./gradlew build

## Publish locally

This will publish the JAR in your local Maven repository

    ./gradlew publishToMavenLocal

## Publish

This will upload the plugin to Nexus repository

    ./gradlew build publish

## Usage

To use this plugin add the maven repository on settings.gradle (must be the first block of the file)

    pluginManagement {
      repositories {
        mavenCentral()
      }
    }

then add the plugin on build.gradle

    plugins {
        id "com.ekino.oss.gradle.plugin.java" version "1.0.0"
    }
