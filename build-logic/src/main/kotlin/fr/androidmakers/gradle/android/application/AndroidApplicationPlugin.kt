package fr.androidmakers.gradle.android.application

import com.android.build.gradle.BaseExtension
import fr.androidmakers.gradle.addRepositories
import fr.androidmakers.gradle.android.configureAndroidCompileSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
    private val versionMajor = 1
    private val versionMinor = 4
    private val versionPatch = 0

    override fun apply(project: Project) {
        project.apply(mapOf("plugin" to "com.android.application"))
        project.apply(mapOf("plugin" to "kotlin-android"))
        project.apply(mapOf("plugin" to "com.google.firebase.crashlytics"))

        addRepositories(project)
        checkGoogleServices(project)

        configureAndroidCompileSdk(project)

        project.extensions.findByType(BaseExtension::class.java)!!.apply {
            defaultConfig.apply {
                applicationId = "fr.paug.androidmakers"
                minSdk = 21
                targetSdk = 31
                versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10
                versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                multiDexEnabled = true

                val properties = try {
                    Properties().apply {
                        load(project.file("buildconfig.properties").inputStream())
                    }
                } catch (e: Exception) {
                    null
                }

                for (key in listOf(
                    "YOUTUBE_API_KEY",
                    "OPENFEEDBACK_API_KEY",
                    "OPENFEEDBACK_PROJECT_ID",
                    "OPENFEEDBACK_APPLICATION_ID"
                )) {
                    val value = (properties?.get(key) as? String) ?: "${key}_DUMMY"
                    buildConfigField("String", key, "\"$value\"")
                }
            }

            val f = project.file("keystore.properties")
            signingConfigs.apply {
                val props = Properties()
                if (f.exists()) {
                    props.load(f.reader())
                }
                create("release").apply {
                    keyAlias = props.getProperty("keyAlias")
                    keyPassword = props.getProperty("keyAliasPassword")
                    storeFile = project.file("keystore.jks")
                    storePassword = props.getProperty("keyAliasPassword")
                }
            }

            buildTypes.apply {
                getByName("release").apply {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android.txt"),
                        "proguard-rules.pro"
                    )
                    if (f.exists()) {
                        signingConfig = signingConfigs.getByName("release")
                    }
                }
            }

            @Suppress("UnstableApiUsage")
            this.buildFeatures.viewBinding = true
        }

        project.apply(mapOf("plugin" to "com.google.gms.google-services"))
    }

    private fun checkGoogleServices(project: Project) {
        val target = project.file("google-services.json")
        val source = project.file("google-services.json.mock")
        if (!target.exists()) {
            println("using mock google-services.json")
            source.copyTo(target)
        }
    }
}