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

configurations.configureEach {
  // Remove unnecessary dependency of Precompose
  exclude(group = "androidx.appcompat", module = "appcompat")
}

android {
  namespace = "fr.paug.androidmakers.di"
}
