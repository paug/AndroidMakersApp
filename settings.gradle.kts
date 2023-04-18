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

plugins {
  id("com.gradle.enterprise") version "3.13"
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("libs.versions.toml"))
    }
  }
}


include(":app")
include(":store")
include(":store-graphql")
