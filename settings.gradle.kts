@file:Suppress("UnstableApiUsage")

include(":shared:di")

pluginManagement {
  listOf(repositories, dependencyResolutionManagement.repositories).forEach {
    it.apply {
      google()
      mavenCentral()
      gradlePluginPortal()
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
