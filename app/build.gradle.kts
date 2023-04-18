plugins {
  id("fr.androidmakers.gradle.android.application")
}

dependencies {
  implementation(project(":store-graphql"))
  implementation(project(":store-firebase"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.espresso.core)

  implementation(libs.multidex)

  // Kotlin
  implementation(libs.kotlinx.coroutines.play.services)
  implementation(libs.kotlinx.coroutines.android)

  // Support
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)

  implementation(libs.material)
  implementation(libs.vectordrawable)
  implementation(libs.browser)
  implementation(libs.percentlayout)
  implementation(libs.cardview)
  implementation(libs.constraintlayout)
  implementation(libs.shared.preferences)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.crashlytics.ktx)
  implementation(libs.firebase.analytics.ktx)
  implementation(libs.firebase.messaging)
  implementation(libs.firebase.config)
  implementation(libs.firebase.auth)

  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.compose.material3)
  implementation(libs.compose.material.icons.extended)
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.tooling)
  implementation(libs.navigation.compose)
  implementation(libs.accompanist.pager)
  implementation(libs.accompanist.systemuicontroller)
  implementation(libs.accompanist.pager.indicators)
  implementation(libs.accompanist.flowlayout)

  implementation(libs.play.services.auth)

  // Image management
  implementation(libs.glide.runtime)
  add("annotationProcessor", libs.glide.compiler)
  implementation(libs.coil.compose)

  // Used for tags
  implementation(libs.layouts)
  implementation(libs.openfeedback)
}
