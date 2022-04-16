package fr.androidmakers.gradle

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
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

fun Project.catalogVersion(name: String): String = extensions.findByType(VersionCatalogsExtension::class.java)!!
    .named("libs")
    .findVersion(name)
    .get()
    .displayName

fun Project.catalogDependency(name: String): String = extensions.findByType(VersionCatalogsExtension::class.java)!!
    .named("libs")
    .findDependency(name)
    .get()
    .get()
    .run {
        "$module:${versionConstraint.displayName}"
    }