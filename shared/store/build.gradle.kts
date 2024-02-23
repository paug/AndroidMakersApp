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
      baseName = "store"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
        api(libs.kotlinx.datetime)
      }
  }
}

android {
  namespace = "fr.androidmakers.store"
}
