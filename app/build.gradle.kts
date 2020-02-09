import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.fabric")
    id("kotlin-android-extensions")
}

val versionMajor = 1
val versionMinor = 2
val versionPatch = 5

fun checkGoogleServices(variant: String) {
    val target = project.file("src/$variant/google-services.json")
    val source = project.file("src/$variant/google-services.json.mock")
    if (!target.exists()) {
        System.out.println("using mock google-services.json")
        source.copyTo(target)
    }
}

checkGoogleServices("prod")
checkGoogleServices("preprod")

extensions.findByType(BaseExtension::class.java)!!.apply {
    compileSdkVersion(28.toString().toInt())
    defaultConfig {
        applicationId = "fr.paug.androidmakers"
        minSdkVersion(15.toString())
        targetSdkVersion(28.toString())
        versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true

        buildConfigField("String", "YOUTUBE_API_KEY", "\"" + System.getProperty("androidMakersYouTubeApiKey", "dummyAPIKey") + "\"")
    }

    flavorDimensions("environment")

    productFlavors {
        create("prod") {
            setDimension("environment")
        }
        create("preprod") {
            setDimension("environment")
            applicationIdSuffix = ".preprod"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val toto:String by extra
dependencies {
    add("testImplementation", "junit:junit:4.12")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1", {
        exclude(group= "com.android.support", module="support-annotations")
    })

    add("implementation", "com.android.support:multidex:1.0.3")

    // Kotlin
    add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")

    // Support
    add("implementation", "androidx.appcompat:appcompat:1.0.2")
    add("implementation", "com.google.android.material:material:1.0.0")
    add("implementation", "androidx.vectordrawable:vectordrawable:1.0.1")
    add("implementation", "androidx.browser:browser:1.0.0")
    add("implementation", "androidx.percentlayout:percentlayout:1.0.0")
    add("implementation", "androidx.cardview:cardview:1.0.0")
    add("implementation", "androidx.emoji:emoji:1.0.0")
    add("implementation", "androidx.constraintlayout:constraintlayout:1.1.3")

    // Firebase
    add("implementation", "com.google.firebase:firebase-core:16.0.8")
    add("implementation", "com.google.firebase:firebase-messaging:17.6.0")
    add("implementation", "com.firebase:firebase-jobdispatcher:0.8.5")
    add("implementation", "com.crashlytics.sdk.android:crashlytics:2.9.9")
    add("implementation", "com.google.firebase:firebase-config:16.5.0")

    // Firestore
    add("implementation", "com.google.firebase:firebase-firestore:18.2.0")
    add("implementation", "com.google.firebase:firebase-auth:16.2.1")
    add("implementation", "com.firebaseui:firebase-ui-auth:4.3.1")
    add("implementation", "com.google.android.gms:play-services-auth:16.0.1")

    // Image management
    add("implementation", "com.github.bumptech.glide:glide:${Versions.glide}")
    add("annotationProcessor", "com.github.bumptech.glide:compiler:${Versions.glide}")

    // Used for tags
    add("implementation", "org.apmem.tools:layouts:1.10@aar")

    // For Video preview
    add("implementation", files("libs/YouTubeAndroidPlayerApi.jar"))

    add("implementation", "com.google.ar.sceneform.ux:sceneform-ux:1.8.0")
    add("implementation", "com.google.ar.sceneform:core:1.8.0")
}

apply(plugin = "com.google.gms.google-services")
apply(plugin = "com.google.ar.sceneform.plugin")
