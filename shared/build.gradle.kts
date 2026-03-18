plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
}

kotlin {

  listOf(
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "shared"
      isStatic = true

      // https://youtrack.jetbrains.com/issue/KT-70202/Xcode-16-Linker-fails-with-SIGBUS
      linkerOpts("-ld_classic")
      linkerOpts("-ObjC")
      linkerOpts("-framework", "CoreLocation")
      linkerOpts("-framework", "MapKit")

      export(libs.kotlinx.datetime)
      export(project(":shared:ui"))
      export(project(":shared:domain"))
      export(project(":shared:di"))
      export(libs.openfeedback.m3)
      export(libs.openfeedback.viewmodel)
    }
  }

  android {
    namespace = "fr.paug.androidmakers.shared"
  }

  sourceSets {
    commonMain.dependencies {
      api(project(":shared:ui"))
      api(project(":shared:domain"))
      api(project(":shared:di"))
      implementation(libs.jetbrains.compose.runtime)
    }
  }
}

configurations.configureEach {
  // Remove unnecessary transitive dependency
  exclude(group = "androidx.appcompat", module = "appcompat")
}
