plugins {
    id("fr.androidmakers.gradle.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain").apply {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }
    }
}

android {
    namespace = "fr.androidmakers.store"
}