import com.android.build.gradle.BaseExtension
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.fabric")
    id("kotlin-android-extensions")
}

val versionMajor = 1
val versionMinor = 2
val versionPatch = 5

fun checkGoogleServices() {
    val target = project.file("google-services.json")
    val source = project.file("google-services.json.mock")
    if (!target.exists()) {
        System.out.println("using mock google-services.json")
        source.copyTo(target)
    }
}

checkGoogleServices()

extensions.findByType(BaseExtension::class.java)!!.apply {
    compileSdkVersion(28.toString().toInt())
    defaultConfig {
        applicationId = "fr.paug.androidmakers"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        val properties = try {
            Properties().apply {
                load(file("buildconfig.properties").inputStream())
            }
        } catch (e: Exception) {
            null
        }

        for (key in listOf ("YOUTUBE_API_KEY", "OPENFEEDBACK_API_KEY", "OPENFEEDBACK_PROJECT_ID", "OPENFEEDBACK_APPLICATION_ID")) {
            val value = (properties?.get(key) as? String) ?: "${key}_DUMMY"
            buildConfigField("String", key, "\"$value\"")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.dataBinding = true

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    ndkVersion = "21.0.6011959"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    add("testImplementation", "junit:junit:4.12")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1", {
        exclude(group= "com.android.support", module="support-annotations")
    })

    implementation("com.android.support:multidex:1.0.3")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    implementation("com.google.firebase:firebase-firestore-ktx:21.4.0")

    // Support
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.vectordrawable:vectordrawable:1.0.1")
    implementation("androidx.browser:browser:1.0.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.emoji:emoji:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta3")

    // Firebase
    implementation("com.google.firebase:firebase-core:16.0.8")
    implementation("com.google.firebase:firebase-messaging:17.6.0")
    implementation("com.firebase:firebase-jobdispatcher:0.8.5")
    implementation("com.crashlytics.sdk.android:crashlytics:2.9.9")
    implementation("com.google.firebase:firebase-config:16.5.0")

    // Firestore
    implementation("com.google.firebase:firebase-firestore:18.2.0")
    implementation("com.google.firebase:firebase-auth:16.2.1")
    implementation("com.firebaseui:firebase-ui-auth:4.3.1")
    implementation("com.google.android.gms:play-services-auth:16.0.1")

    // Image management
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    add("annotationProcessor", "com.github.bumptech.glide:compiler:${Versions.glide}")

    // Used for tags
    implementation("org.apmem.tools:layouts:1.10@aar")

    // For Video preview
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

    implementation("com.google.ar.sceneform.ux:sceneform-ux:1.8.0")
    implementation("com.google.ar.sceneform:core:1.8.0")

    implementation("androidx.compose:compose-runtime:${Versions.compose}")
    implementation("androidx.ui:ui-framework:${Versions.compose}")
    implementation("androidx.ui:ui-layout:${Versions.compose}")
    implementation("androidx.ui:ui-material:${Versions.compose}")
    implementation("androidx.ui:ui-foundation:${Versions.compose}")
    implementation("androidx.ui:ui-animation:${Versions.compose}")
    implementation("androidx.ui:ui-tooling:${Versions.compose}")

    implementation(project(":openfeedback"))
    implementation(project(":openfeedback-ui"))
}

apply(plugin = "com.google.gms.google-services")
apply(plugin = "com.google.ar.sceneform.plugin")
