@file:Suppress("UnstableApiUsage")

include(":shared:di")

pluginManagement {
  listOf(repositories, dependencyResolutionManagement.repositories).forEach {
    it.apply {
      google()
      mavenCentral()
      gradlePluginPortal()
      maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
  }
  includeBuild("build-logic")
}
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(
    ":androidApp",
    ":wearApp",
  ":desktopApp",
    ":shared",
    ":shared:domain",
    ":shared:data",
    ":shared:ui"
)
