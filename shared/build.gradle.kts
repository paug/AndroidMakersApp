plugins {
  alias(libs.plugins.androidmakers.kmp.library)
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
    }
  }
}

android {
  namespace = "com.androidmakers.shared"
}
