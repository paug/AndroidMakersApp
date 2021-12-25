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
        create("fr.androidmakers.gradle.library") {
            id = "fr.androidmakers.gradle.library"
            implementationClass = "fr.androidmakers.gradle.library.LibraryPlugin"
        }
    }
}