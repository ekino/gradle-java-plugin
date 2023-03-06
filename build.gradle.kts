/*
 * Copyright (c) 2021 ekino (https://www.ekino.com/)
 */

import se.bjurr.gitchangelog.plugin.gradle.GitChangelogTask

plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  jacoco
  id("net.researchgate.release") version "3.0.2"
  id("se.bjurr.gitchangelog.git-changelog-gradle-plugin") version "1.78.0"
  id("org.sonarqube") version "3.5.0.2730"
  id("com.gradle.plugin-publish") version "0.21.0"
}

repositories {
  gradlePluginPortal()
  mavenCentral()
}

dependencies {
  implementation("org.unbroken-dome.gradle-plugins:gradle-testsets-plugin:${property("testsetsPluginVersion")}")

  testImplementation(gradleTestKit())
  testImplementation("org.junit.jupiter:junit-jupiter:${property("junitJupiterVersion")}")
  testImplementation("io.strikt:strikt-jvm:${property("striktVersion")}")
}

tasks.test {
  useJUnitPlatform()

  // waiting for https://github.com/gradle/gradle/issues/5431 in order to have a better way to do that
  addTestListener(object : TestListener {
    override fun beforeSuite(suite: TestDescriptor) {}
    override fun beforeTest(testDescriptor: TestDescriptor) {}
    override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
      if (suite.parent == null) {
        println("\nTests result: ${result.resultType}")
        println("Tests summary: ${result.testCount} tests, " +
            "${result.successfulTestCount} succeeded, " +
            "${result.failedTestCount} failed, " +
            "${result.skippedTestCount} skipped")
      }
    }
  })
}

val pluginName = "gradleJava"
gradlePlugin {
  plugins {
    register(pluginName) {
      id = "com.ekino.oss.gradle.plugin.java"
      implementationClass = "com.ekino.oss.gradle.plugin.java.JavaPlugin"
    }
  }
}

pluginBundle {
  website = "https://github.com/ekino/gradle-java-plugin"
  vcsUrl = "https://github.com/ekino/gradle-java-plugin"
  description = "Java plugin applying some configuration for your builds (mavenPublish, testSets, etc ...)"

  (plugins) {
    named(pluginName) {
      displayName = "Java plugin"
      tags = listOf("ekino", "java", "build", "junit5", "publish")
      version = version
    }
  }
}

val gitChangelogTask by tasks.registering(GitChangelogTask::class) {
  file = File("CHANGELOG.md")
  templateContent = file("changelog.mustache").readText()
}

tasks.jacocoTestReport {
  reports {
    xml.required.set(true)
  }
}

sonarqube {
  properties {
    property("sonar.projectKey", "ekino_gradle-java-plugin")
    property("sonar.java.coveragePlugin", "jacoco")
    property("sonar.junit.reportPaths", "${buildDir}/test-results/test")
    property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/test/jacocoTestReport.xml")
  }
}
