plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.google.services)
  alias(libs.plugins.androidmakers.android.signing)
  alias(libs.plugins.jetbrainsCompose)
}

android {
  namespace = "fr.paug.androidmakers.wear"
  compileSdk = 34

  defaultConfig {
    applicationId = "fr.paug.androidmakers"
    minSdk = 30
    targetSdk = 33
    versionCode = libs.versions.version.code.get().toInt()
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    isCoreLibraryDesugaringEnabled = true
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

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
            "/*.proto",
            "google/**",
            "src/google/**",
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
  implementation(libs.play.services.wearable)
  implementation(libs.play.services.auth)
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
  implementation(libs.firebase.auth)
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  debugImplementation(libs.compose.ui.tooling)

  implementation(libs.koin.androidx.compose) {
    exclude(group = "androidx.appcompat", module = "appcompat")
  }
  implementation(project(":shared:di"))
  implementation(project(":shared:domain"))
}

configurations.configureEach {
  // Remove bogus dependency of Horologist, which itself depends on AppCompat and Material Components
  exclude(group = "androidx.navigation", module = "navigation-ui-ktx")
}

compose {
  kotlinCompilerPlugin.set(libs.versions.compose.compiler.get())
}
