package fr.androidmakers.plugin

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.kotlin.multiplatform.library")
      }

      extensions.getByType<KotlinMultiplatformExtension>().apply {
        applyDefaultHierarchyTemplate()
        jvm()
      }

      val androidTarget = extensions.getByType<KotlinMultiplatformExtension>()
        .targets.getByName("android") as KotlinMultiplatformAndroidLibraryTarget
      androidTarget.compileSdk = libs.findVersion("sdk.compile").get().displayName.toInt()
      androidTarget.minSdk = libs.findVersion("sdk.min").get().displayName.toInt()

      configureKotlin()
    }
  }
}
