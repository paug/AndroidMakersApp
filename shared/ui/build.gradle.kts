plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko)
  alias(libs.plugins.jetbrainsCompose)
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
      api(libs.moko.resources)
      api(libs.moko.compose)
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
      implementation(libs.openfeedback.m3)
      implementation(libs.materii.pullrefresh)

      implementation(project(":shared:domain"))
      implementation(project(":shared:di"))
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.ui"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
