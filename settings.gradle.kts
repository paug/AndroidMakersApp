@file:Suppress("UnstableApiUsage")
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
    ":shared:store",
    ":shared:store-graphql"
)
