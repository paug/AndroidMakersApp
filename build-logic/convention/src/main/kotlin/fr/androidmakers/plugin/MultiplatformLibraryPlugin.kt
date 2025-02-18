package fr.androidmakers.plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.library")
      }

      (kotlinExtension as KotlinMultiplatformExtension).apply {
        applyDefaultHierarchyTemplate()
        androidTarget()

        jvm {
          compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
          }
        }
      }

      val extension = extensions.getByType<LibraryExtension>()
      configureKotlinAndroid(extension)
    }
  }
}
