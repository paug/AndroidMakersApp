package fr.androidmakers.gradle.plugin

import fr.androidmakers.gradle.androidSetup
import fr.androidmakers.gradle.commonSetup
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformLibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "com.android.library"))
        project.apply(mapOf("plugin" to "org.jetbrains.kotlin.multiplatform"))

        project.commonSetup()
        project.androidSetup()

        (project.kotlinExtension as KotlinMultiplatformExtension).apply {
            android()
        }
    }
}