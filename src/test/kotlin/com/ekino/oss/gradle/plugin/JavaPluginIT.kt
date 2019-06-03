/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.gradle.plugin

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.contentOf
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
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

    assertThat(result.tasks).hasSize(11)
    assertThat(result.task(":compileJava")).isNotNull()
    assertThat(result.task(":processResources")).isNotNull()
    assertThat(result.task(":jar")).isNotNull()
    assertThat(result.task(":assemble")).isNotNull()
    assertThat(result.task(":compileTestJava")).isNotNull()
    assertThat(result.task(":processTestResources")).isNotNull()
    assertThat(result.task(":testClasses")).isNotNull()
    assertThat(result.task(":test")).isNotNull()
    assertThat(result.task(":compileIntegrationTestJava")).isNull()
    assertThat(result.task(":processIntegrationTestResources")).isNull()
    assertThat(result.task(":integrationTestClasses")).isNull()
    assertThat(result.task(":integrationTest")).isNull()

    assertThat(result.task(":test")?.outcome).isEqualTo(SUCCESS)
    assertThat(result.task(":build")?.outcome).isEqualTo(SUCCESS)

    val testReportsDir = tempDir.resolve("build")
        .resolve("reports")
        .resolve("tests")
        .resolve("test")
    assertThat(testReportsDir).exists()

    assertThat(File("$tempDir/build/libs").listFiles()).hasSize(1)

    val yaml = Files.newDirectoryStream(Paths.get("$tempDir/build/resources/main"), "*.yml").toList()
    assertThat(yaml).hasSize(1)
    assertThat(contentOf(yaml[0].toFile())).contains("version: 1.0.0", "description: test_stuff")
  }

  @Test
  fun `Should build project with integration tests`() {
    val result = runTask("project_with_test_and_integration_test")

    assertThat(result.tasks).hasSize(15)
    assertThat(result.task(":compileJava")).isNotNull()
    assertThat(result.task(":processResources")).isNotNull()
    assertThat(result.task(":jar")).isNotNull()
    assertThat(result.task(":assemble")).isNotNull()
    assertThat(result.task(":compileTestJava")).isNotNull()
    assertThat(result.task(":processTestResources")).isNotNull()
    assertThat(result.task(":testClasses")).isNotNull()
    assertThat(result.task(":test")).isNotNull()
    assertThat(result.task(":compileIntegrationTestJava")).isNotNull()
    assertThat(result.task(":processIntegrationTestResources")).isNotNull()
    assertThat(result.task(":integrationTestClasses")).isNotNull()
    assertThat(result.task(":integrationTest")).isNotNull()

    assertThat(result.task(":test")?.outcome).isEqualTo(SUCCESS)
    assertThat(result.task(":build")?.outcome).isEqualTo(SUCCESS)

    val testReportsDir = tempDir.resolve("build")
        .resolve("reports")
        .resolve("tests")
    assertThat(testReportsDir.resolve("test")).exists()
    assertThat(testReportsDir.resolve("integrationTest")).exists()
  }

  @Test
  fun `Should display test report in output`() {
    val result = runTask("project_with_test")

    assertThat(result.output).contains("Tests summary:", "1 tests", "1 succeeded", "0 failed", "0 skipped")
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
