package fr.androidmakers.gradle.android

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal val Project.androidBaseExtension: BaseExtension
    get() {
        return extensions.findByName("android") as BaseExtension?
            ?: error("no 'android' extension found")
    }

fun configureAndroidCompileSdk(project: Project) {
    project.androidBaseExtension.compileSdkVersion(31)
}