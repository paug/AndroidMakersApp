import com.apollographql.apollo.annotations.ApolloExperimental

plugins {
  alias(libs.plugins.androidmakers.kmp.library)
  alias(libs.plugins.apollo)
}

kotlin {

  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    jvmMain.dependencies {
      implementation(libs.okhttp)
    }

    commonMain {
      dependencies {
        api(project(":shared:domain"))
        api(libs.apollo.runtime)
        implementation(libs.apollo.adapters.kotlinx.datetime)
        implementation(libs.apollo.normalized.cache.sqlite)

        api(libs.androidx.datastore.preferences)
        api(libs.androidx.datastore.preferences.core)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.okio) // Used by DataStore and others

        api(libs.firebase.auth)
      }
    }

    androidMain {
      dependencies {
        implementation(project.dependencies.platform(libs.firebase.bom))
        implementation(libs.okhttp)  // Used by Apollo
        implementation(libs.play.services.wearable)
      }
    }
  }

  android {
    namespace = "fr.androidmakers.store.graphql"
  }
}

apollo {
  service("service") {
    packageName.set("fr.androidmakers.store.graphql")
    @OptIn(ApolloExperimental::class)
    generateDataBuilders.set(true)
    mapScalar("GraphQLLocalDateTime", "kotlinx.datetime.LocalDateTime", "com.apollographql.adapter.datetime.KotlinxLocalDateTimeAdapter")
    mapScalar("GraphQLInstant", "kotlinx.datetime.Instant", "fr.androidmakers.store.graphql.KotlinxInstantAdapter")
    mapScalarToKotlinString("Markdown")

    @OptIn(ApolloExperimental::class)
    plugin("com.apollographql.cache:normalized-cache-apollo-compiler-plugin:${libs.versions.apollo.cache.get()}") {
      argument("com.apollographql.cache.packageName", packageName.get())
    }

    introspection {
      schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
      endpointUrl.set("https://androidmakers.fr/graphql")
      //endpointUrl.set("http://10.1.3.174:8080/graphql")
    }
  }
}
