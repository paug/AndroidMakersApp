package fr.androidmakers.gradle.android.library

import fr.androidmakers.gradle.addRepositories
import fr.androidmakers.gradle.androidSetup
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "com.android.library"))
        project.apply(mapOf("plugin" to "kotlin-android"))

        project.androidSetup()

        addRepositories(project)
    }
}