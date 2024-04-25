plugins {
  alias(libs.plugins.androidmakers.android.application)
  alias(libs.plugins.androidmakers.android.compose)
  alias(libs.plugins.androidmakers.android.signing)
}

android {
  defaultConfig {
    namespace = "fr.paug.androidmakers"
    versionCode = libs.versions.version.code.get().toInt()
    versionName = libs.versions.version.code.get()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures.buildConfig = true
}

dependencies {
  implementation(project(":shared:data"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.espresso.core)

  // Kotlin
  implementation(libs.kotlinx.coroutines.play.services)
  implementation(libs.kotlinx.coroutines.android)

  // Support
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.runtime)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.crashlytics.ktx)
  implementation(libs.firebase.inappmessaging)
  implementation(libs.firebase.messaging)

  implementation(libs.play.services.auth)

  // Image management
  implementation(libs.coil.compose)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)

  // Used for tags
  implementation(project(":shared"))
}
