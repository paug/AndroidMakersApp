package fr.androidmakers.gradle

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal val Project.androidExtension: BaseExtension
  get() {
    return extensions.findByName("android") as BaseExtension?
      ?: error("no 'android' extension found")
  }

private fun configureAndroidCompileSdk(project: Project) {
  project.androidExtension.compileSdkVersion(31)
}

fun Project.androidSetup() {
  configureKotlinCompiler(project)
  configureAndroidCompileSdk(project)
  enableCoreLibraryDesugaring()
}

fun Project.enableCoreLibraryDesugaring() {

  androidExtension.defaultConfig {
    it.multiDexEnabled = true
  }
  androidExtension.compileOptions {
    it.isCoreLibraryDesugaringEnabled = true
  }

  dependencies.add(
    "coreLibraryDesugaring",
    dependencies.create(catalogDependency("desugar-jdk-libs"))
  )
}