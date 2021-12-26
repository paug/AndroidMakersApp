package fr.androidmakers.gradle.multiplatform.library

import fr.androidmakers.gradle.addRepositories
import fr.androidmakers.gradle.android.configureAndroidCompileSdk
import fr.androidmakers.gradle.configureKotlinCompiler
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformLibraryPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "com.android.library"))
        project.apply(mapOf("plugin" to "org.jetbrains.kotlin.multiplatform"))

        addRepositories(project)
        configureAndroidCompileSdk(project)
        configureKotlinCompiler(project)
        (project.kotlinExtension as KotlinMultiplatformExtension).apply {
            android()
        }
    }
}