plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.moko)
}

kotlin {

  listOf(
      iosX64(),
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "ui"
      isStatic = true
      export(libs.moko.resources)
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(libs.moko.resources)
    }

    val androidMain by getting {
      dependsOn(commonMain.get())
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain.get())
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
    }
  }
}

android {
  namespace = "com.androidmakers.ui"
}

multiplatformResources {
  multiplatformResourcesPackage = "com.androidmakers.ui"
}
