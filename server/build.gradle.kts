plugins {
  id("fr.androidmakers.gradle.jvm.library")
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.appengine)
}

dependencies {
  implementation(libs.graphql.kotlin.spring.server)
  implementation(libs.kotlinx.datetime)
  implementation(libs.kotlinx.serialization)

  testImplementation(libs.kotlin.test.junit)
  testImplementation(libs.okhttp)
}

appengine {
  stage {
    setArtifact(tasks.named("bootJar").flatMap { (it as Jar).archiveFile })
  }
  deploy {
    projectId = "kiki-conf"
    version = "GCLOUD_CONFIG"
  }
}