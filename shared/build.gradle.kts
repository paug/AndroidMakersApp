plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko)
  alias(libs.plugins.skie)
}

kotlin {

  listOf(
      iosX64(),
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "shared"
      isStatic = true
      export(project(":shared:ui"))
      export(project(":shared:domain"))
      export(project(":shared:di"))
      export(libs.moko.resources)
      export(libs.moko.graphics)
      export(libs.openfeedback)
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.moko.resources)

      // Temporary
      api(libs.precompose.koin)
      api(project(":shared:ui"))
      api(project(":shared:domain"))
      api(project(":shared:di"))
      api(libs.openfeedback)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.shared"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
