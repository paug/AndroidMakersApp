pluginManagement {
  listOf(repositories, dependencyResolutionManagement.repositories).forEach {
    it.apply {
      google()
      mavenCentral()
      maven("https://androidx.dev/storage/compose-compiler/repository")
      gradlePluginPortal()
    }
  }
  resolutionStrategy {
    eachPlugin {
      if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
        useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
      }
    }
  }
  includeBuild("build-logic")
}

include(":app")
include(":store")
include(":store-graphql")
