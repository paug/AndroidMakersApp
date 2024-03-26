plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.google.services)

  alias(libs.plugins.androidmakers.android.signing)
}

android {
  namespace = "fr.paug.androidmakers.wear"
  compileSdk = 34

  defaultConfig {
    applicationId = "fr.paug.androidmakers"
    minSdk = 30
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    isCoreLibraryDesugaringEnabled = true
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(libs.play.services.wearable)
  implementation(libs.play.services.auth)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.splashscreen)
  implementation(libs.wear.compose.material)
  implementation(libs.wear.compose.foundation)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.horologist.composables)
  implementation(libs.horologist.compose.layout)
  implementation(libs.horologist.compose.material)
  implementation(libs.horologist.auth.ui)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.wear.compose.navigation)
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.auth.ktx)
  implementation(libs.kprefs)
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  debugImplementation(libs.compose.ui.tooling)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)
  implementation(project(":shared:di"))
  implementation(project(":shared:domain"))
}
