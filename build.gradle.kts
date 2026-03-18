plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.google.services) apply false
  alias(libs.plugins.kmp) apply false
  alias(libs.plugins.crashlytics) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.detekt)
}

detekt {
  buildUponDefaultConfig = true
  config.setFrom(files("detekt.yml"))
  baseline = file("detekt-baseline.xml")
  source.setFrom(
    files(
      fileTree(rootDir) {
        include("**/src/*/kotlin/**/*.kt", "**/src/*/java/**/*.kt")
        exclude("**/build/**")
      }
    )
  )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
  reports {
    html.required.set(true)
    sarif.required.set(true)
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.layout.buildDirectory)
}
