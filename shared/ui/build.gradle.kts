plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  applyDefaultHierarchyTemplate()

  iosX64()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.ui)
      implementation(compose.material3)
      implementation(compose.materialIconsExtended)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.coil.compose)
      implementation(libs.jetbrains.navigation.compose)
      implementation(libs.jetbrains.lifecycle.runtime.compose)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      api(libs.openfeedback.viewmodel)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.okio)   // Used by Openfeedback

      implementation(project(":shared:domain"))
      implementation(project(":shared:di"))
    }
    androidMain.dependencies {
      implementation(libs.coil.network.okhttp)
    }
    iosMain.dependencies {
      implementation(libs.coil.network.ktor3)
      implementation(libs.ktor.client.darwin)
    }
  }
}

configurations.configureEach {
  // Remove unnecessary transitive dependency
  exclude(group = "androidx.appcompat", module = "appcompat")
  // Disable Android Drawable support in Coil
  exclude(group = "com.google.accompanist", module = "accompanist-drawablepainter")
}

android {
  namespace = "fr.paug.androidmakers.ui"
}

compose.resources {
  packageOfResClass = "fr.paug.androidmakers.ui"
}
