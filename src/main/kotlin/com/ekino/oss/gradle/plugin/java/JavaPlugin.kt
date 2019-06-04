/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.gradle.plugin.java

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the
import org.unbrokendome.gradle.plugins.testsets.TestSetsPlugin
import org.unbrokendome.gradle.plugins.testsets.dsl.testSets

class JavaPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    with(project) {

      // plugins
      apply<org.gradle.api.plugins.JavaPlugin>()
      apply<TestSetsPlugin>()
      val sourceSets = the<SourceSetContainer>()
      apply<MavenPublishPlugin>()

      // properties
      setProperty("sourceCompatibility", JavaVersion.VERSION_11)
      setProperty("targetCompatibility", JavaVersion.VERSION_11)

      addTasks()

      configure(listOf(project)) {

        // Setup default task
        defaultTasks("build")

        tasks.getByName<Copy>("processResources") {
          from(sourceSets["main"].resources.srcDirs) {
            include("**/*.yml")
            filter {
              it
                  .replace("\${project-version}", project.version as String)
                  .replace("\${project-description}", project.description as String)
            }
          }
        }

        configureTests()

        configureIntegrationTests()
      }

      configurePublishing()
    }
  }

  private fun Project.addTasks() {
    tasks.register<Copy>("aggregateJunitReports") {
      from("$buildDir/test-results/test", "$buildDir/test-results/integrationTest")
      into("$buildDir/test-results/all")
    }
  }

  private fun Project.configureTests() {
    tasks.withType(Test::class.java) {
      // To use JUnit5 Jupiter
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
  }

  private fun Project.configureIntegrationTests() {
    if (file("$projectDir/src/it").exists()) {

      testSets {
        "integrationTest" {
          dirName = "it"
        }
      }

      val test by tasks.named("test")
      val integrationTest by tasks.named("integrationTest") {
        mustRunAfter(test)
      }

      tasks.named("check") {
        dependsOn(integrationTest)
      }

      tasks.named("aggregateJunitReports") {
        dependsOn(test, integrationTest)
      }
    }
  }

  private fun Project.configurePublishing() {
    if (plugins.hasPlugin("maven-publish")
        && hasProperty("publishingBaseUrl")
        && hasProperty("publishingLogin")
        && hasProperty("publishingPassword")) {

      extensions.configure<PublishingExtension> {
        publications.register<MavenPublication>("mavenJava") {
          from(components["java"])
        }
      }

      repositories {
        maven {
          val publishingBaseUrl = findProperty("publishingBaseUrl") as String
          val projectVersion = project.version as String

          url = uri("$publishingBaseUrl${if (projectVersion.endsWith("-SNAPSHOT")) "snapshots" else "releases"}/")

          credentials {
            username = findProperty("publishingLogin") as String
            password = findProperty("publishingPassword") as String
          }
        }
      }
    }
  }
}
