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
      api(libs.qdsfdhvh.imageloader)
      api(libs.precompose)
      api(libs.precompose.viewmodel)
      api(libs.precompose.koin)
      api(libs.openfeedback.m3)
      implementation(libs.materii.pullrefresh)

      implementation(project(":shared:domain"))
      implementation(project(":shared:di"))
    }
  }
}

configurations.configureEach {
  // Remove unnecessary dependency of Precompose and Moko
  exclude(group = "androidx.appcompat", module = "appcompat")
}

android {
  namespace = "fr.paug.androidmakers.ui"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
