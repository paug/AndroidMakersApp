plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.google.services) apply false
  alias(libs.plugins.kmp) apply false
  alias(libs.plugins.crashlytics) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.moko.resources) apply false
}

tasks.register("clean", Delete::class) {
  delete(rootProject.layout.buildDirectory)
}
