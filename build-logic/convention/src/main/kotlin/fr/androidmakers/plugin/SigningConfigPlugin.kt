package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

class SigningConfigPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      val extension = extensions.getByType<ApplicationExtension>()
      extension.apply {
        val f = project.rootProject.file("androidApp/keystore.properties")
        signingConfigs.apply {
          val props = Properties()
          if (f.exists()) {
            props.load(f.reader())
          }
          create("release").apply {
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyAliasPassword")
            storeFile = project.rootProject.file("androidApp/keystore.release")
            storePassword = props.getProperty("keyAliasPassword")
          }

          getByName("debug").apply {
            keyAlias = "debug"
            keyPassword = "androidmakers"
            storeFile = project.rootProject.file("androidApp/keystore.debug")
            storePassword = "androidmakers"
          }
        }

        buildTypes.apply {
          getByName("release").apply {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                "proguard-defaults.txt",
                "proguard-rules.pro"
            )
            if (f.exists()) {
              signingConfig = signingConfigs.getByName("release")
            }
          }
        }
      }
    }
  }
}
