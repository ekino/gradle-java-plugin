# gradle java plugin

Java gradle plugin for Ekino projects

## Overview

This plugin configures the following tasks for any Ekino Java project :

* Apply the [Gradle Java plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
* Set the source/target compatibility to `1.11` java version
* Define the `integrationTest` source set (`it` directory) : [unbroken-dome/gradle-testsets-plugin](https://github.com/unbroken-dome/gradle-testsets-plugin)
* Define the test report files aggregation
* Replace `${project-version}` and `${project-description}` in YAML files by the gradle project version
* Define JUnit Platform configuration for JUnit 5 (Jupiter)
* Define the artifact publication to Frida repository

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
        gradlePluginPortal()
        mavenCentral()
      }
    }

then add the plugin on build.gradle

    plugins {
        id "com.ekino.oss.java" version "0.0.1"
    }
