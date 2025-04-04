plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

  iosX64()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    androidMain.dependencies {
      implementation(libs.koin.android)
    }

    commonMain.dependencies {
      implementation(project(":shared:domain"))
      implementation(project(":shared:data"))
      implementation(libs.apollo.normalized.cache.sqlite)
      implementation(libs.okio)

      api(libs.koin.core)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.di"
  buildFeatures.buildConfig = true
}
