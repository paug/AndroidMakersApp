plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko)
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
      export(libs.moko.resources)
      export(project(":shared:ui"))
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.moko.resources)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.ui"
}

multiplatformResources {
  resourcesPackage.set("fr.paug.androidmakers.ui")
}
