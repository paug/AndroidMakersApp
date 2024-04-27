import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

repositories {
  google()
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.firebase.crashlytics.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
}

group = "fr.paug.androidmakers.build"

gradlePlugin {
  plugins {
    register("androidApplication") {
      id = "fr.androidmakers.android.application"
      implementationClass = "fr.androidmakers.plugin.AndroidApplicationPlugin"
    }

    register("kmpLibrary") {
      id = "fr.androidmakers.kmp.library"
      implementationClass = "fr.androidmakers.plugin.MultiplatformLibraryPlugin"
    }
    
    register("androidSigning") {
      id = "fr.androidmakers.android.signing"
      implementationClass = "fr.androidmakers.plugin.SigningConfigPlugin"
    }
  }
}
