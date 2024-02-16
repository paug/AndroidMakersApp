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
    ":app",
    ":store",
    ":store-graphql"
)
