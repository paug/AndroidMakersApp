plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  applyDefaultHierarchyTemplate()

  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.ui)
      implementation("org.jetbrains.compose.material3:material3:1.11.0-alpha03")
      implementation(compose.materialIconsExtended)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.coil.compose)
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
