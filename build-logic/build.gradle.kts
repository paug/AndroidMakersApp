plugins {
    `embedded-kotlin`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.google.services)
    implementation(libs.firebase.crashlytics.gradle)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("fr.androidmakers.gradle.android.application") {
            id = "fr.androidmakers.gradle.android.application"
            implementationClass = "fr.androidmakers.gradle.android.application.AndroidApplicationPlugin"
        }
        create("fr.androidmakers.gradle.android.library") {
            id = "fr.androidmakers.gradle.android.library"
            implementationClass = "fr.androidmakers.gradle.android.library.AndroidLibraryPlugin"
        }
        create("fr.androidmakers.gradle.jvm.library") {
            id = "fr.androidmakers.gradle.jvm.library"
            implementationClass = "fr.androidmakers.gradle.jvm.library.JvmLibraryPlugin"
        }
        create("fr.androidmakers.gradle.multiplatform.library") {
            id = "fr.androidmakers.gradle.multiplatform.library"
            implementationClass = "fr.androidmakers.gradle.multiplatform.library.MultiplatformLibraryPlugin"
        }
    }
}