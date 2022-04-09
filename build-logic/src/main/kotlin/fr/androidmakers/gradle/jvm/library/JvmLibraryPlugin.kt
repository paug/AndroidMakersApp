package fr.androidmakers.gradle.jvm.library

import fr.androidmakers.gradle.addRepositories
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "org.jetbrains.kotlin.jvm"))

        addRepositories(project)
    }
}