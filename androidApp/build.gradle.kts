plugins {
  alias(libs.plugins.androidmakers.android.application)
  alias(libs.plugins.androidmakers.android.signing)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
}

android {
  defaultConfig {
    namespace = "fr.paug.androidmakers"
    versionCode = libs.versions.version.code.get().toInt()
    versionName = libs.versions.version.code.get()
    androidResources.localeFilters += listOf("en", "fr")
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
  }
  buildFeatures.buildConfig = true

  buildTypes {
    release {
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
            "META-INF/*.version",
            "META-INF/versions/**",
            "META-INF/androidx/**"
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

  implementation(compose.material3)
  implementation(compose.materialIconsExtended)
  implementation(compose.uiTooling)
  coreLibraryDesugaring(libs.desugar.jdk.libs)

  // Kotlin
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.play.services)

  // Support
  implementation(libs.androidx.core)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.activity.compose)
  implementation(libs.jetbrains.lifecycle.runtime)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.crashlytics.ktx)
  implementation(libs.firebase.messaging)

  // Credentials
  implementation(libs.androidx.credentials)
  implementation(libs.androidx.credentials.playServicesAuth)
  implementation(libs.googleid)

  implementation(libs.koin.androidx.compose) {
    exclude(group = "androidx.appcompat", module = "appcompat")
  }

  // Used for tags
  implementation(project(":shared"))
}
