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
      baseName = "domain"
      isStatic = true
      export(libs.kotlinx.datetime)
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.datetime)
      implementation(libs.okio)
    }
    androidMain.dependencies {
      implementation(libs.androidx.core)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.domain"
}
