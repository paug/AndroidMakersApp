package fr.androidmakers.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      val extension = extensions.getByType<ApplicationExtension>()
      extension.apply {
        buildFeatures.compose = true

        composeOptions {
          kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().toString()
        }

        dependencies {
          add("implementation", libs.findLibrary("compose.material3").get())
          add("implementation", libs.findLibrary("compose.material.icons.extended").get())
          add("implementation", libs.findLibrary("compose.ui.tooling").get())
        }
      }
    }
  }
}
