import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm").version("1.6.0")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.0-alpha05")
    implementation("com.google.gms:google-services:4.3.10")
    implementation("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${getKotlinPluginVersion()}")
}
