package fr.androidmakers.gradle

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension


fun configureKotlinCompiler(project: Project) {
  project.kotlinExtension.sourceSets.all {
    it.languageSettings {
      optIn("kotlin.RequiresOptIn")
    }
  }
}

fun Project.catalogVersion(name: String): String = extensions.findByType(VersionCatalogsExtension::class.java)!!
    .named("libs")
    .findVersion(name)
    .get()
    .displayName

fun Project.catalogDependency(name: String): String = extensions.findByType(VersionCatalogsExtension::class.java)!!
    .named("libs")
    .findLibrary(name)
    .get()
    .get()
    .run {
      "$module:${versionConstraint.displayName}"
    }

fun Project.commonSetup() {
  val bytecodeVersion = "1.8"

  extensions.findByType(JavaPluginExtension::class.java)!!.apply {
    toolchain {
      it.languageVersion.set(JavaLanguageVersion.of(19))
    }

    sourceCompatibility = JavaVersion.toVersion(bytecodeVersion)
    targetCompatibility = JavaVersion.toVersion(bytecodeVersion)
  }

  tasks.withType(KotlinCompile::class.java).configureEach {
    it.kotlinOptions {
      if (this is KotlinJvmOptions) {
        jvmTarget = bytecodeVersion
      }
    }
  }

  extensions.findByName("android").apply {
    this as BaseExtension
    compileOptions {
      it.sourceCompatibility = JavaVersion.toVersion(bytecodeVersion)
      it.targetCompatibility = JavaVersion.toVersion(bytecodeVersion)
    }
  }
}