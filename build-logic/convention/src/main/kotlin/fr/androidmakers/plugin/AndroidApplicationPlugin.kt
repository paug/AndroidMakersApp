package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("com.google.gms.google-services")
        apply("com.google.firebase.crashlytics")
      }

      val extension = extensions.getByType<ApplicationExtension>()
      extension.defaultConfig.targetSdk = libs.findVersion("sdk.compile").get().displayName.toInt()
      configureAndroid(extension)
      configureKotlin()
    }
  }
}

internal fun Project.configureAndroid(extension: ApplicationExtension) {
  extension.compileSdk = libs.findVersion("sdk.compile").get().displayName.toInt()
  extension.defaultConfig.minSdk = libs.findVersion("sdk.min").get().displayName.toInt()
  extension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
  extension.compileOptions.targetCompatibility = JavaVersion.VERSION_17
}

internal fun Project.configureAndroid(extension: com.android.build.api.dsl.LibraryExtension) {
  extension.compileSdk = libs.findVersion("sdk.compile").get().displayName.toInt()
  extension.defaultConfig.minSdk = libs.findVersion("sdk.min").get().displayName.toInt()
  extension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
  extension.compileOptions.targetCompatibility = JavaVersion.VERSION_17
}

internal fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      jvmTarget = JvmTarget.JVM_17
    }
  }
}
