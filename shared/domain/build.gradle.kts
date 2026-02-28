plugins {
  alias(libs.plugins.androidmakers.kmp.library)
}

kotlin {

  iosArm64()
  iosSimulatorArm64()

  android {
    namespace = "fr.paug.androidmakers.domain"
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.datetime)
    }
    commonTest.dependencies {
      implementation(libs.kotlin.test)
      implementation(libs.kotlinx.coroutines.test)
    }
    androidMain.dependencies {
      implementation(libs.androidx.core)
    }
    jvmMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
    }
  }
}
