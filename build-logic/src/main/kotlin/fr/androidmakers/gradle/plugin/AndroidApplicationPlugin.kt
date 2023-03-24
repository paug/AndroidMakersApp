package fr.androidmakers.gradle.plugin

import com.android.build.gradle.BaseExtension
import fr.androidmakers.gradle.androidSetup
import fr.androidmakers.gradle.catalogVersion
import fr.androidmakers.gradle.commonSetup
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
  private val versionMajor = 1
  private val versionMinor = 4
  private val versionPatch = 5

  override fun apply(project: Project) {
    project.apply(mapOf("plugin" to "com.android.application"))
    project.apply(mapOf("plugin" to "kotlin-android"))
    project.apply(mapOf("plugin" to "com.google.firebase.crashlytics"))
    project.apply(mapOf("plugin" to "com.google.gms.google-services"))

    project.commonSetup()
    project.androidSetup()

    project.extensions.findByName("android")!!.apply {
      this as BaseExtension
      defaultConfig.apply {
        namespace = "fr.paug.androidmakers"
        minSdk = 21
        targetSdk = 31
        versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }

      compileOptions {
        // Sets Java compatibility to Java 8
        it.sourceCompatibility = JavaVersion.VERSION_1_8
        it.targetCompatibility = JavaVersion.VERSION_1_8
      }

      @Suppress("UnstableApiUsage")
      composeOptions {
        val composeVersion = project.catalogVersion("compose.compiler")
        it.kotlinCompilerExtensionVersion = composeVersion
      }

      buildFeatures.compose = true

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

      @Suppress("UnstableApiUsage")
      this.buildFeatures.viewBinding = true
    }
  }
}