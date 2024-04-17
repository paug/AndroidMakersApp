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
      baseName = "di"
      isStatic = true
    }
  }

  sourceSets {
    androidMain.dependencies {
      implementation(libs.koin.android)
      implementation(libs.koin.androidx.compose)
    }

    commonMain.dependencies {
      implementation(project(":shared:domain"))
      implementation(project(":shared:data"))
      implementation(libs.apollo.normalized.cache.sqlite)

      api(libs.koin.core)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.di"
}
