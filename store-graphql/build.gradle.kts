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
                implementation(libs.apollo.normalized.cache.sqlite)
                implementation(libs.apollo.normalized.cache)
            }
        }
    }
}

apollo {
    packageName.set("fr.androidmakers.store.graphql")

    introspection {
        schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
        endpointUrl.set("https://kiki-conf.ew.r.appspot.com/graphql")
    }
}
