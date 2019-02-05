/*
 * Copyright (c) 2019 ekino (https://www.ekino.com/)
 */

package com.ekino.oss.gradle.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class JavaPluginTest {

  @Test
  void shouldContainsDefaultPlugins() {
    Project project = ProjectBuilder.builder().build()

    assertEquals(0, project.getExtensions().getPlugins().size())
    project.apply plugin: 'com.ekino.oss.gradle.plugin.java'

    assertTrue(project.pluginManager.hasPlugin('java'))
    assertTrue(project.pluginManager.hasPlugin('org.unbroken-dome.test-sets'))
    assertTrue(project.pluginManager.hasPlugin('maven-publish'))
    assertNotNull(project.getTasksByName('aggregateJunitReports', false))
  }
}
