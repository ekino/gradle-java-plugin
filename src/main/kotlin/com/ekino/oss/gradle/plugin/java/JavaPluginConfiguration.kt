package com.ekino.oss.gradle.plugin.java

import org.gradle.api.JavaVersion

internal const val EXTENSION_NAME = "javaPlugin"

internal open class JavaPluginConfiguration(
        var sourceCompatibility: JavaVersion = JavaVersion.VERSION_11,
        var targetCompatibility: JavaVersion = JavaVersion.VERSION_11
)
