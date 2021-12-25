package fr.androidmakers.gradle

import org.gradle.api.Project

fun addRepositories(project: Project) {
    project.repositories.apply {
        google()
        mavenCentral()
    }
}