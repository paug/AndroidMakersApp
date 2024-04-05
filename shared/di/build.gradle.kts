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
      api(libs.koin.android)
      api(libs.koin.androidx.compose)
    }

    commonMain.dependencies {
      implementation(project(":shared:domain"))
      implementation(project(":shared:data"))

      api(libs.koin.core)
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.di"
}
