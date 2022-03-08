package fr.androidmakers.gradle.library

import fr.androidmakers.gradle.addRepositories
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "org.jetbrains.kotlin.jvm"))

        addRepositories(project)
    }
}