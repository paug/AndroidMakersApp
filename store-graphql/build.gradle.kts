plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.apollo)
}

kotlin {

  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }

  androidTarget()

  sourceSets {
    commonMain {
      dependencies {
        api(project(":store"))
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
  compileSdk = libs.versions.sdk.compile.get().toInt()
  defaultConfig {
    minSdk = libs.versions.sdk.min.get().toInt()
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
      endpointUrl.set("https://androidmakers-2023.ew.r.appspot.com/graphql")
    }
  }
}
