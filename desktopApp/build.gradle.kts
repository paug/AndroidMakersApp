import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.kmp)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  jvm()

  sourceSets {
    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(project(":shared"))
      implementation(libs.kotlinx.coroutines.swing)
    }
  }
}

compose.desktop {
  application {
    mainClass = "fr.paug.androidmakers.desktop.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "AndroidMakers"
      packageVersion = "1.0.0"
    }
  }
}
