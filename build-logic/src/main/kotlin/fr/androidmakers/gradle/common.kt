package fr.androidmakers.gradle

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

fun addRepositories(project: Project) {
    project.repositories.apply {
        google()
        mavenCentral()
    }
}

fun configureKotlinCompiler(project: Project) {
    project.kotlinExtension.sourceSets.all {
        it.languageSettings {
            optIn("kotlin.RequiresOptIn")
        }
    }
}