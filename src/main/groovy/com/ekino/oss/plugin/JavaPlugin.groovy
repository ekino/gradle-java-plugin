/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.testing.Test

class JavaPlugin implements Plugin<Project> {

  private static final String JAVA_VERSION = "1.11"
  private static final String NEXUS_URL = 'https://nexus.ekino.com'

  @Override
  void apply(Project project) {

    // plugins
    project.plugins.apply('java')
    project.plugins.apply('org.unbroken-dome.test-sets')
    project.plugins.apply('maven-publish')

    // properties
    project.setProperty("sourceCompatibility", JAVA_VERSION)
    project.setProperty("targetCompatibility", JAVA_VERSION)

    // tasks
    project.task('aggregateJunitReports', type: Copy) {
      from "${project.buildDir}/test-results/test"
      from "${project.buildDir}/test-results/integrationTest"
      into "${project.buildDir}/test-results/all"
    }

    // configuration
    project.configure(project) {

      // Setup default task
      defaultTasks 'build'

      // Replace ${project-version} and ${project-description} tokens in Yaml files
      processResources {
        from(sourceSets.main.resources.srcDirs) {
          include '**/*.yml'
          filter {
            String line -> line.replace("\${project-version}", project.version)
                               .replace("\${project-description}", project.description)
          }
        }
      }

      // Artefacts publishing configuration
      if (project.hasProperty('publishingBaseUrl')
        && project.hasProperty('publishingLogin')
        && project.hasProperty('publishingPassword')) {
        // Nota Bene : variables ${publishingBaseUrl}, ${publishingLogin} and ${publishingPassword}
        // are provided using Gitlab CI/CD variables (cf Settings > CI/CD > Variables)
        // and prefixed by "ORG_GRADLE_PROJECT_"
        publishing {
          publications {
            mavenJava(MavenPublication) {
              from components.java
            }
          }
          repositories {
            maven {
              url = "${publishingBaseUrl}${project.version.endsWith('-SNAPSHOT') ? 'snapshots' : 'releases'}/"
              credentials {
                username = "${publishingLogin}"
                password = "${publishingPassword}"
              }
            }
          }
        }
      }

      // Tests configuration
      tasks.withType(Test) {
        // To use JUnit5 Jupiter
        useJUnitPlatform()

        // Tests summary (displayed at the end)
        afterSuite { desc, result ->
          if (!desc.parent) {
            println "\nTests result: ${result.resultType}"
            println "Tests summary: ${result.testCount} tests, " +
                    "${result.successfulTestCount} succeeded, " +
                    "${result.failedTestCount} failed, " +
                    "${result.skippedTestCount} skipped"
          }
        }
      }

      if (file("${projectDir}/src/it").exists()) {

        testSets {
          integrationTest {
            dirName = 'it'
          }
        }

        // TODO(sau) to remove after testsets plugin issue would be fixed (https://github.com/unbroken-dome/gradle-testsets-plugin/issues/55)
        sourceSets {
          integrationTest {
            java {
              srcDir "src/it/java"
            }
            resources {
              srcDir "src/it/resources"
            }
          }
        }

        check.dependsOn integrationTest
        integrationTest.mustRunAfter test

        aggregateJunitReports.dependsOn test, integrationTest
      }
    }
  }
}
