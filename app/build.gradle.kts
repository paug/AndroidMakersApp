import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.google.services)
  alias(libs.plugins.crashlytics)
  alias(libs.plugins.kotlin.serialization)
}

android {
  defaultConfig {
    namespace = "fr.paug.androidmakers"
    minSdk = libs.versions.sdk.min.get().toInt()
    targetSdk = libs.versions.sdk.compile.get().toInt()
    compileSdk = libs.versions.sdk.compile.get().toInt()
    versionCode = libs.versions.version.code.get().toInt()
    versionName = libs.versions.version.code.get()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    // Sets Java compatibility to Java 8
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }

  buildFeatures.compose = true
  buildFeatures.buildConfig = true

  val f = project.file("keystore.properties")
  signingConfigs.apply {
    val props = Properties()
    if (f.exists()) {
      props.load(f.reader())
    }
    create("release").apply {
      keyAlias = props.getProperty("keyAlias")
      keyPassword = props.getProperty("keyAliasPassword")
      storeFile = project.file("keystore.release")
      storePassword = props.getProperty("keyAliasPassword")
    }

    getByName("debug").apply {
      keyAlias = "debug"
      keyPassword = "androidmakers"
      storeFile = project.file("keystore.debug")
      storePassword = "androidmakers"
    }
  }

  buildTypes.apply {
    getByName("release").apply {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
          getDefaultProguardFile("proguard-android.txt"),
          "proguard-rules.pro"
      )
      if (f.exists()) {
        signingConfig = signingConfigs.getByName("release")
      }
    }
  }

  buildFeatures.viewBinding = true
}

dependencies {
  implementation(project(":store-graphql"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.espresso.core)

  implementation(libs.multidex)

  // Kotlin
  implementation(libs.kotlinx.coroutines.play.services)
  implementation(libs.kotlinx.coroutines.android)

  // Support
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.lifecycle.runtimeCompose)

  implementation(libs.material)
  implementation(libs.vectordrawable)
  implementation(libs.browser)
  implementation(libs.percentlayout)
  implementation(libs.constraintlayout)
  implementation(libs.shared.preferences)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.auth)
  implementation(libs.firebase.config)
  implementation(libs.firebase.crashlytics.ktx)
  implementation(libs.firebase.inappmessaging)
  implementation(libs.firebase.messaging)

  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.compose.material3)
  implementation(libs.compose.material.icons.extended)
  implementation(libs.compose.ui.tooling)
  implementation(libs.navigation.compose)

  implementation(libs.accompanist.pager)
  implementation(libs.accompanist.pager.indicators)

  implementation(libs.play.services.auth)

  // Image management
  implementation(libs.glide.runtime)
  add("annotationProcessor", libs.glide.compiler)
  implementation(libs.coil.compose)

  // Used for tags
  implementation(libs.layouts)
  implementation(libs.openfeedback)
}
