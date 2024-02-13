plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }

  androidTarget()

  sourceSets {
    commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
        api(libs.kotlinx.datetime)
      }
  }
}

android {
  namespace = "fr.androidmakers.store"
  compileSdk = libs.versions.sdk.compile.get().toInt()
  defaultConfig {
    minSdk = libs.versions.sdk.min.get().toInt()
  }
}
