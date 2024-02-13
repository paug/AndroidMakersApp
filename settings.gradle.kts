@file:Suppress("UnstableApiUsage")

pluginManagement {
  listOf(repositories, dependencyResolutionManagement.repositories).forEach {
    it.apply {
      google()
      mavenCentral()
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

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}


include(
    ":app",
    ":store",
    ":store-graphql"
)
