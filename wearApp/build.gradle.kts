plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.google.services)
  alias(libs.plugins.androidmakers.android.signing)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "fr.paug.androidmakers.wear"
  compileSdk = 35

  defaultConfig {
    applicationId = "fr.paug.androidmakers"
    minSdk = 30
    targetSdk = 33
    versionCode = libs.versions.version.code.get().toInt()
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    isCoreLibraryDesugaringEnabled = true
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildTypes {
    release {
      packaging {
        resources {
          excludes += listOf(
            "DebugProbesKt.bin",
            "kotlin-tooling-metadata.json",
            "/*.properties",
            "kotlin/**",
            "/*.proto",
            "google/**",
            "src/google/**",
            "META-INF/*.version",
            "META-INF/androidx/**",
            "core/**",
            "java/**"
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
  implementation(libs.play.services.wearable)
  implementation(libs.play.services.auth)
  implementation(libs.androidx.core)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.splashscreen)
  implementation(libs.wear.compose.material)
  implementation(libs.wear.compose.foundation)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.compose.material.icons.extended)
  implementation(libs.horologist.composables)
  implementation(libs.horologist.compose.layout)
  implementation(libs.horologist.compose.material)
  implementation(libs.horologist.auth.ui)
  implementation(libs.wear.compose.ui.tooling)
  implementation(libs.wear.compose.navigation)
  implementation(platform(libs.firebase.bom))
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.firebase.auth)
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  debugImplementation(libs.wear.compose.ui.tooling)

  implementation(libs.androidx.datastore.preferences) // Used by Horologist Auth
  implementation(libs.okio)                           // Used by DataStore

  implementation(libs.koin.androidx.compose) {
    exclude(group = "androidx.appcompat", module = "appcompat")
  }
  implementation(project(":shared:di"))
  implementation(project(":shared:domain"))
}
