plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.apollo)
}

kotlin {

  sourceSets {
    commonMain {
      dependencies {
        api(project(":shared:store"))
        implementation(libs.apollo.runtime)
        implementation(libs.apollo.adapters)
        implementation(libs.apollo.normalized.cache.sqlite)
        implementation(libs.apollo.normalized.cache)
      }
    }

    androidMain.dependencies {
        implementation(libs.firebase.auth)
        implementation(libs.firebase.auth.ktx)
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
