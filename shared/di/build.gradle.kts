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

configurations.configureEach {
  // Remove unnecessary dependency of Precompose and Koin Android
  exclude(group = "androidx.appcompat", module = "appcompat")
}

android {
  namespace = "fr.paug.androidmakers.di"
  buildFeatures.buildConfig = true
}
