plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

  iosX64()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.datetime)
    }
    androidMain.dependencies {
      implementation(libs.androidx.core)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.domain"
}
