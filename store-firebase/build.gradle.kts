plugins {
    id("fr.androidmakers.gradle.multiplatform.library")
}

kotlin {
    sourceSets {
        getByName("commonMain").apply {
            dependencies {
            }
        }
        getByName("androidMain").apply {
            dependencies {
                api(project(":store"))
                implementation("com.google.firebase:firebase-firestore")
                implementation("com.google.firebase:firebase-firestore-ktx")
                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
}