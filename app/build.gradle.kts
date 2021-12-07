import com.android.build.gradle.BaseExtension
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.firebase.crashlytics")
}

val versionMajor = 1
val versionMinor = 3
val versionPatch = 3

fun checkGoogleServices() {
    val target = project.file("google-services.json")
    val source = project.file("google-services.json.mock")
    if (!target.exists()) {
        println("using mock google-services.json")
        source.copyTo(target)
    }
}

checkGoogleServices()

extensions.findByType(BaseExtension::class.java)!!.apply {
    compileSdkVersion(31)

    defaultConfig {
        applicationId = "fr.paug.androidmakers"
        minSdk = 21
        targetSdk = 31
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

    val f = project.file("keystore.properties")
    signingConfigs {
        val props = Properties()
        if (f.exists()) {
            props.load(f.reader())
        }
        create("release") {
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyAliasPassword")
            storeFile = project.file("keystore.jks")
            storePassword = props.getProperty("keyAliasPassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            if (f.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    this.buildFeatures.viewBinding = true
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    add("testImplementation", "junit:junit:4.12")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1") {
        exclude(group = "com.android.support", module = "support-annotations")
    }

    implementation("com.android.support:multidex:1.0.3")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Support
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.emoji:emoji:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.1"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    implementation("com.google.android.gms:play-services-auth:19.2.0")

    // Image management
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    add("annotationProcessor", "com.github.bumptech.glide:compiler:${Versions.glide}")

    // Used for tags
    implementation("org.apmem.tools:layouts:1.10@aar")
}

apply(plugin = "com.google.gms.google-services")
