plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

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
