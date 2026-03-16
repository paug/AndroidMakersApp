package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.File
import java.util.Properties

class SigningConfigPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      val extension = extensions.getByType<ApplicationExtension>()
      val keystoreFile = project.rootProject.file("androidApp/keystore.properties")
      extension.apply {
        configureSigningConfigs(keystoreFile)
        configureBuildTypes(keystoreFile)
      }
    }
  }
}

private fun ApplicationExtension.configureSigningConfigs(keystoreFile: File) {
  val props = Properties()
  if (keystoreFile.exists()) {
    props.load(keystoreFile.reader())
  }
  signingConfigs.apply {
    create("release").apply {
      keyAlias = props.getProperty("keyAlias")
      keyPassword = props.getProperty("keyAliasPassword")
      storeFile = keystoreFile.parentFile.resolve("keystore.release")
      storePassword = props.getProperty("keyAliasPassword")
    }

    getByName("debug").apply {
      keyAlias = "debug"
      keyPassword = "androidmakers"
      storeFile = keystoreFile.parentFile.resolve("keystore.debug")
      storePassword = "androidmakers"
    }
  }
}

private fun ApplicationExtension.configureBuildTypes(keystoreFile: File) {
  buildTypes.apply {
    getByName("release").apply {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        "proguard-defaults.txt",
        "proguard-rules.pro"
      )
      if (keystoreFile.exists()) {
        signingConfig = signingConfigs.getByName("release")
      }
    }
  }
}
