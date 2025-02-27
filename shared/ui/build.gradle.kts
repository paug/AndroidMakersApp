plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko.resources)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  applyDefaultHierarchyTemplate()

  listOf(
      iosX64(),
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "ui"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.moko.resources.compose)
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.materialIconsExtended)
      implementation(compose.components.uiToolingPreview)
      api(libs.coil.compose)
      api(libs.jetbrains.navigation.compose)
      api(libs.jetbrains.lifecycle.viewmodel)
      api(libs.koin.compose)
      api(libs.koin.compose.viewmodel)
      api(libs.openfeedback.m3)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.okio)   // Used by Openfeedback
      implementation(libs.materii.pullrefresh)

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
  // Remove unnecessary dependency of Precompose and Moko
  exclude(group = "androidx.appcompat", module = "appcompat")
  // Disable Android Drawable support in Coil
  exclude(group = "com.google.accompanist", module = "accompanist-drawablepainter")
}

android {
  namespace = "fr.paug.androidmakers.ui"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
