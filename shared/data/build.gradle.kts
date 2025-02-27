plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.apollo)
}

kotlin {

  listOf(
      iosX64(),
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "data"
      isStatic = true
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        api(project(":shared:domain"))
        api(libs.apollo.runtime)
        implementation(libs.apollo.adapters.kotlinx.datetime)
        implementation(libs.apollo.normalized.cache.sqlite)
        implementation(libs.apollo.normalized.cache)

        api(libs.androidx.datastore.preferences)
        implementation(libs.okio) // Used by DataStore and others

        api(libs.firebase.auth)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.okhttp)  // Used by Apollo
        implementation(libs.play.services.wearable)
      }
    }
  }
}

android {
  namespace = "fr.androidmakers.store.graphql"
}

dependencies {
  implementation(platform(libs.firebase.bom))
}

apollo {
  service("service") {
    packageName.set("fr.androidmakers.store.graphql")
    generateDataBuilders.set(true)
    mapScalar("GraphQLLocalDateTime", "kotlinx.datetime.LocalDateTime", "com.apollographql.adapter.datetime.KotlinxLocalDateTimeAdapter")

    introspection {
      schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
      endpointUrl.set("https://androidmakers.fr/graphql")

      // This header is not needed to fetch the schema but it's read by the Apollo IDE plugin which will inject it when executing queries
      headers.set(mapOf("conference" to "androidmakers2024"))
    }
  }
}
