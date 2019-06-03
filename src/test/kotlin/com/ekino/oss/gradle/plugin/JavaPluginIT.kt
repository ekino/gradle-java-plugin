/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.gradle.plugin

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.exists
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class JavaPluginIT {

  @TempDir
  lateinit var tempDir: Path

  @Test
  fun `Should build project with only unit tests`() {
    val result = runTask("project_with_test")

    expectThat(result.tasks).hasSize(11)
    expectThat(result.task(":compileJava")).isNotNull()
    expectThat(result.task(":processResources")).isNotNull()
    expectThat(result.task(":jar")).isNotNull()
    expectThat(result.task(":assemble")).isNotNull()
    expectThat(result.task(":compileTestJava")).isNotNull()
    expectThat(result.task(":processTestResources")).isNotNull()
    expectThat(result.task(":testClasses")).isNotNull()
    expectThat(result.task(":test")).isNotNull()
    expectThat(result.task(":compileIntegrationTestJava")).isNull()
    expectThat(result.task(":processIntegrationTestResources")).isNull()
    expectThat(result.task(":integrationTestClasses")).isNull()
    expectThat(result.task(":integrationTest")).isNull()

    expectThat(result.task(":test")?.outcome).isEqualTo(SUCCESS)
    expectThat(result.task(":build")?.outcome).isEqualTo(SUCCESS)

    val testReportsDir = tempDir.resolve("build")
        .resolve("reports")
        .resolve("tests")
        .resolve("test")
    expectThat(testReportsDir).exists()

    expectThat(File("$tempDir/build/libs").listFiles().size).isEqualTo(1)

    val yaml = Files.newDirectoryStream(Paths.get("$tempDir/build/resources/main"), "*.yml").toList()
    expectThat(yaml).hasSize(1)
    expectThat(yaml[0].toFile().readText()) {
      contains("version: 1.0.0")
      contains("description: test_stuff")
    }
  }

  @Test
  fun `Should build project with integration tests`() {
    val result = runTask("project_with_test_and_integration_test")

    expectThat(result.tasks).hasSize(15)
    expectThat(result.task(":compileJava")).isNotNull()
    expectThat(result.task(":processResources")).isNotNull()
    expectThat(result.task(":jar")).isNotNull()
    expectThat(result.task(":assemble")).isNotNull()
    expectThat(result.task(":compileTestJava")).isNotNull()
    expectThat(result.task(":processTestResources")).isNotNull()
    expectThat(result.task(":testClasses")).isNotNull()
    expectThat(result.task(":test")).isNotNull()
    expectThat(result.task(":compileIntegrationTestJava")).isNotNull()
    expectThat(result.task(":processIntegrationTestResources")).isNotNull()
    expectThat(result.task(":integrationTestClasses")).isNotNull()
    expectThat(result.task(":integrationTest")).isNotNull()

    expectThat(result.task(":test")?.outcome).isEqualTo(SUCCESS)
    expectThat(result.task(":build")?.outcome).isEqualTo(SUCCESS)

    val testReportsDir = tempDir.resolve("build")
        .resolve("reports")
        .resolve("tests")
    expectThat(testReportsDir.resolve("test")).exists()
    expectThat(testReportsDir.resolve("integrationTest")).exists()
  }

  @Test
  fun `Should display test report in output`() {
    val result = runTask("project_with_test")

    expectThat(result.output) {
      contains("Tests summary:")
      contains("1 tests")
      contains("1 succeeded")
      contains("0 failed")
      contains("0 skipped")
    }
  }

  private fun runTask(project: String): BuildResult {
    File("src/test/resources/$project").copyRecursively(tempDir.toFile())

    return GradleRunner.create()
        .withArguments("build")
        .withProjectDir(tempDir.toFile())
        .withTestKitDir(tempDir.toFile())
        .withPluginClasspath()
        .forwardOutput()
        .build()
  }
}
