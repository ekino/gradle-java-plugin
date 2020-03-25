package com.ekino.oss.gradle.plugin

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.File

class GradleVersionsCompatibilityTest {

    @TempDir
    lateinit var tempDir: File

    @ValueSource(strings = ["5.6.4", "6.2.2", "6.3"])
    @ParameterizedTest(name = "Gradle {0}")
    @DisplayName("Should work in Gradle version")
    fun shouldWorkInGradleVersion(gradleVersion: String) {
        val buildScript =
            """
            plugins {
                id 'com.ekino.oss.gradle.plugin.java'
            }
            """

        File("$tempDir/build.gradle").writeText(buildScript)

        val result = GradleRunner.create()
            .withProjectDir(tempDir)
            .withGradleVersion(gradleVersion)
            .withPluginClasspath()
            .withArguments("build", "--stacktrace")
            .forwardOutput()
            .build()

        expectThat(result.task(":build")?.outcome) isEqualTo TaskOutcome.SUCCESS
    }
}
