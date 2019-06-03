/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.gradle.plugin

import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.JavaVersion
import org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestFramework
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

class JavaPluginTest {

  @Test
  fun shouldContainsDefaultPlugins() {
    val project = ProjectBuilder.builder().build()

    assertThat(project.plugins.size).isZero()

    project.plugins.apply("com.ekino.oss.gradle.plugin.java")

    assertThat(project.pluginManager.hasPlugin("java")).isEqualTo(true)
    assertThat(project.pluginManager.hasPlugin("org.unbroken-dome.test-sets")).isEqualTo(true)
    assertThat(project.pluginManager.hasPlugin("maven-publish")).isEqualTo(true)
    assertThat(project.getTasksByName("aggregateJunitReports", false)).isNotNull()
  }

  @Test
  fun shouldTargetJava11ByDefault() {
    val project = ProjectBuilder.builder().build()

    project.plugins.apply("com.ekino.oss.gradle.plugin.java")

    val properties = project.properties
    assertThat(properties["sourceCompatibility"]).isEqualTo(JavaVersion.VERSION_11)
    assertThat(properties["targetCompatibility"]).isEqualTo(JavaVersion.VERSION_11)
  }

  @Test
  fun shouldSetBuildAsDefaultTask() {
    val project = ProjectBuilder.builder().build()

    project.plugins.apply("com.ekino.oss.gradle.plugin.java")

    val defaultTasks = project.defaultTasks
    assertThat(defaultTasks)
            .hasSize(1)
            .containsOnly("build")
  }

  @Test
  fun shouldSetupJunit5() {
    val project = ProjectBuilder.builder().build()

    project.plugins.apply("com.ekino.oss.gradle.plugin.java")

    val testTasks = project.tasks.withType(org.gradle.api.tasks.testing.Test::class.java)
    assertThat(testTasks.getByName("test").testFramework)
            .isExactlyInstanceOf(JUnitPlatformTestFramework::class.java)
  }
}
