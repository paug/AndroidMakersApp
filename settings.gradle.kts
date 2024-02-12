@file:Suppress("UnstableApiUsage")

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
