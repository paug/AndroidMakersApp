plugins {
  alias(libs.plugins.androidmakers.android.application)
  alias(libs.plugins.androidmakers.android.signing)
  alias(libs.plugins.jetbrainsCompose)
}

android {
  defaultConfig {
    namespace = "fr.paug.androidmakers"
    versionCode = libs.versions.version.code.get().toInt()
    versionName = libs.versions.version.code.get()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
  }
  buildFeatures.buildConfig = true

  buildTypes {
    release {
      kotlinOptions {
        freeCompilerArgs += listOf(
          "-Xno-param-assertions",
          "-Xno-call-assertions",
          "-Xno-receiver-assertions"
        )
      }

      packaging {
        resources {
          excludes += listOf(
            "DebugProbesKt.bin",
            "kotlin-tooling-metadata.json",
            "/*.properties",
            "kotlin/**",
            "junit/**",
            "LICENSE-junit.txt",
            "/*.proto",
            "google/**",
            "META-INF/*.version"
          )
        }
        jniLibs {
          excludes += "**/libdatastore_shared_counter.so"
        }
      }
    }
  }
}

dependencies {
  implementation(project(":shared:data"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.espresso.core)

  implementation(libs.compose.material3)
  implementation(libs.compose.material.icons.extended)
  implementation(libs.compose.ui.tooling)
  coreLibraryDesugaring(libs.desugar.jdk.libs)

  // Kotlin
  implementation(libs.kotlinx.coroutines.play.services)
  implementation(libs.kotlinx.coroutines.android)

  // Support
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.runtime)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.crashlytics.ktx)
  implementation(libs.firebase.messaging)

  implementation(libs.play.services.auth)

  implementation(libs.koin.androidx.compose) {
    exclude(group = "androidx.appcompat", module = "appcompat")
  }

  // Used for tags
  implementation(project(":shared"))
}

compose {
  kotlinCompilerPlugin.set(libs.versions.compose.compiler.get())
}
