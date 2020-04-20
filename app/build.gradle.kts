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
val versionPatch = 10

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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")
    implementation("com.google.firebase:firebase-firestore-ktx:21.4.2")

    // Support
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("androidx.browser:browser:1.2.0")
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.emoji:emoji:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")

    // Firebase
    implementation("com.google.firebase:firebase-core:17.3.0")
    implementation("com.google.firebase:firebase-messaging:20.1.5")
    implementation("com.firebase:firebase-jobdispatcher:0.8.5")
    implementation("com.crashlytics.sdk.android:crashlytics:2.10.1")
    implementation("com.google.firebase:firebase-config:19.1.3")

    // Firestore
    implementation("com.google.firebase:firebase-firestore:21.4.2")
    implementation("com.google.firebase:firebase-auth:19.3.0")
    implementation("com.firebaseui:firebase-ui-auth:4.3.1")
    implementation("com.google.android.gms:play-services-auth:18.0.0")

    // Image management
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    add("annotationProcessor", "com.github.bumptech.glide:compiler:${Versions.glide}")

    // Used for tags
    implementation("org.apmem.tools:layouts:1.10@aar")

    implementation("io.openfeedback:feedback-android-sdk-ui:0.0.5")
}

apply(plugin = "com.google.gms.google-services")
