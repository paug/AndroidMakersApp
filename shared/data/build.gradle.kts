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
        implementation(libs.apollo.adapters)
        implementation(libs.apollo.normalized.cache.sqlite)
        implementation(libs.apollo.normalized.cache)

        implementation(libs.androidx.datastore.preferences.core)
        api(libs.androidx.datastore.preferences)

        api(libs.firebase.auth)

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
    mapScalar("LocalDateTime", "kotlinx.datetime.LocalDateTime", "com.apollographql.apollo3.adapter.KotlinxLocalDateTimeAdapter")

    introspection {
      schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
      endpointUrl.set("https://androidmakers-2023.ew.r.appspot.com/graphql")
    }
  }
}
