package fr.androidmakers.gradle.plugin

import fr.androidmakers.gradle.commonSetup
import fr.androidmakers.gradle.configureKotlinCompiler
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.apply(mapOf("plugin" to "org.jetbrains.kotlin.jvm"))

    project.commonSetup()
    configureKotlinCompiler(project)
  }
}