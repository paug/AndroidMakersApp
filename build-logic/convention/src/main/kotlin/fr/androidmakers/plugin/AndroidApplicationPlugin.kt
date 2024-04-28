package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
        apply("com.google.gms.google-services")
        apply("com.google.firebase.crashlytics")
      }

      val extension = extensions.getByType<ApplicationExtension>()
      extension.defaultConfig.targetSdk = libs.findVersion("sdk.compile").get().displayName.toInt()
      configureKotlinAndroid(extension)
    }
  }
}

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = libs.findVersion("sdk.compile").get().displayName.toInt()

    defaultConfig {
      minSdk = libs.findVersion("sdk.min").get().displayName.toInt()
    }


    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }
  }

  configureKotlin()
}

private fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_17.toString()
    }
  }
}
