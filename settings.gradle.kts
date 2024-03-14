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

include(
    ":androidApp",
    ":shared",
    ":shared:domain",
    ":shared:data",
    ":shared:ui"
)
