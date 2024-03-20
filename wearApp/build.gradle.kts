plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.google.services)
}

android {
  namespace = "fr.paug.androidmakers.wear"
  compileSdk = 34

  defaultConfig {
    minSdk = 30
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.splashscreen)
  implementation(libs.wear.compose.material)
  implementation(libs.wear.compose.foundation)
  implementation(libs.androidx.material.icons.core)
  implementation(libs.horologist.composables)
  implementation(libs.horologist.compose.layout)
  implementation(libs.horologist.compose.material)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.wear.compose.navigation)
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  debugImplementation(libs.compose.ui.tooling)

  implementation(libs.koin.android)
  implementation(project(":shared:di"))
  implementation(project(":shared:domain"))
}
