plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

  iosArm64()
  iosSimulatorArm64()

  android {
    namespace = "fr.paug.androidmakers.di"
  }

  sourceSets {
    androidMain.dependencies {
      implementation(project.dependencies.platform(libs.firebase.bom))
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
