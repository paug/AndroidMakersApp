plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko)
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
      export(libs.moko.resources)
      export(libs.moko.graphics)
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.moko.resources)
      api(project(":shared:ui"))
      api(project(":shared:domain"))
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.shared"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
