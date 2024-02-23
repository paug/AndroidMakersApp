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
      export(libs.moko.resources)
      export(libs.moko.graphics)
    }
  }
}

android {
  namespace = "com.androidmakers.shared"
}

multiplatformResources {
  multiplatformResourcesPackage = "com.androidmakers.ui"
}
