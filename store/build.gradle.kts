plugins {
    id("fr.androidmakers.gradle.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain").apply {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
