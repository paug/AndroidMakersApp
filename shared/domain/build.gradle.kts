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
      baseName = "domain"
      isStatic = true
      export(libs.kotlinx.datetime)
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.datetime)

      // Temporary dependency to handle the fact that swift doesnt support Kotlin Result
      // It will be removed when we will merge the viewmodels or go for Compose MP
      api("at.asitplus:kmmresult:1.5.4")
    }
  }
}

android {
  namespace = "fr.paug.androidmakers.domain"
}
