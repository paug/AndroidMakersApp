plugins {
  id("fr.androidmakers.gradle.multiplatform.library")
  alias(libs.plugins.apollo)
}

kotlin {
  sourceSets {
    getByName("commonMain").apply {
      dependencies {
        api(project(":store"))
        implementation(libs.apollo.runtime)
        implementation(libs.apollo.adapters)
        implementation(libs.apollo.normalized.cache.sqlite)
        implementation(libs.apollo.normalized.cache)
      }
    }

    getByName("androidMain").apply {
      dependencies {
        implementation(libs.firebase.auth)
        implementation(libs.firebase.auth.ktx)
      }
    }
  }
}

dependencies {
  implementation(platform(libs.firebase.bom))
}

apollo {
  service("service") {
    packageName.set("fr.androidmakers.store.graphql")
    generateDataBuilders.set(true)
    mapScalar("LocalDateTime", "kotlinx.datetime.LocalDateTime", "com.apollographql.apollo3.adapter.KotlinxLocalDateTimeAdapter")

    introspection {
      schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
      endpointUrl.set("https://confetti-app.dev/graphql")
    }
  }
}
