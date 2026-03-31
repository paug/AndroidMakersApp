plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  applyDefaultHierarchyTemplate()

  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    commonMain.dependencies {
      implementation(libs.jetbrains.compose.runtime)
      implementation(libs.jetbrains.compose.foundation)
      implementation(libs.jetbrains.compose.ui)
      implementation(libs.jetbrains.compose.material3)
      implementation(libs.jetbrains.compose.components.resources)
      implementation(libs.jetbrains.compose.ui.tooling.preview)
      implementation(libs.coil.compose)
      implementation(libs.jetbrains.compose.material3.adaptive.navigation3)
      implementation(libs.jetbrains.navigation3.ui)
      implementation(libs.jetbrains.lifecycle.runtime.compose)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.okio)   // Used by Openfeedback

      implementation(project(":shared:domain"))
      implementation(project(":shared:di"))
    }
    androidMain.dependencies {
      implementation(libs.coil.network.okhttp)
      api(libs.openfeedback.viewmodel)
    }
    iosMain.dependencies {
      implementation(libs.coil.network.ktor3)
      implementation(libs.ktor.client.darwin)
      api(libs.openfeedback.viewmodel)
    }
    jvmMain.dependencies {
      implementation(libs.coil.network.ktor3)
      implementation(libs.ktor.client.java)
    }
  }

  android {
    namespace = "fr.paug.androidmakers.ui"
    androidResources.enable = true
  }
}

configurations.configureEach {
  // Remove unnecessary transitive dependency
  exclude(group = "androidx.appcompat", module = "appcompat")
  // Disable Android Drawable support in Coil
  exclude(group = "com.google.accompanist", module = "accompanist-drawablepainter")
}

compose.resources {
  packageOfResClass = "fr.paug.androidmakers.ui"
}
