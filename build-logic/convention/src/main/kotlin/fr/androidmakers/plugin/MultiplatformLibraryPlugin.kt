package fr.androidmakers.plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.library")
      }

      configureMultiplatformLibrary(kotlinExtension)

      (kotlinExtension as KotlinMultiplatformExtension).apply {
        applyDefaultHierarchyTemplate()
        androidTarget()
      }

      val extension = extensions.getByType<LibraryExtension>()
      configureKotlinAndroid(extension)
    }
  }
}

private fun configureMultiplatformLibrary(
    commonExtension: KotlinProjectExtension,
) {
    commonExtension.jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
}
