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
      baseName = "shared"
      isStatic = true
      export(project(":shared:ui"))
      export(project(":shared:domain"))
      export(project(":shared:di"))
      export(libs.openfeedback.m3)
      export(libs.openfeedback.viewmodel)
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(project(":shared:ui"))
      api(project(":shared:domain"))
      api(project(":shared:di"))
    }
  }
}

configurations.configureEach {
  // Remove unnecessary transitive dependency
  exclude(group = "androidx.appcompat", module = "appcompat")
}

android {
  namespace = "fr.paug.androidmakers.shared"
}
