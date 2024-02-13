package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.android.application")
      pluginManager.apply("org.jetbrains.kotlin.android")
      pluginManager.apply("com.google.gms.google-services")
      pluginManager.apply("com.google.firebase.crashlytics")
      pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

      val extension = extensions.getByType<ApplicationExtension>()
      //configureAndroidCompose(extension)
    }
  }
}
