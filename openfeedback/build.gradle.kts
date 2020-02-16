import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.library")
    id("kotlin-android")
}


extensions.findByType(BaseExtension::class.java)!!.apply {
    compileSdkVersion(28.toString().toInt())
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    ndkVersion = "21.0.6011959"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Kotlin
    add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.1")

    // Firestore
    implementation("com.google.firebase:firebase-firestore:21.4.0")
    implementation("com.google.firebase:firebase-auth:19.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:21.4.0")
}

