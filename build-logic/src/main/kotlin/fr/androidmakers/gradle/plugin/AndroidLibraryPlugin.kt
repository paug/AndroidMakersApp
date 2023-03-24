package fr.androidmakers.gradle.plugin

import fr.androidmakers.gradle.androidSetup
import fr.androidmakers.gradle.commonSetup
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "com.android.library"))
        project.apply(mapOf("plugin" to "kotlin-android"))

        project.commonSetup()
        project.androidSetup()
    }
}