plugins {
    id("fr.androidmakers.gradle.android.application")
}

dependencies {
    implementation(project(":store"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.multidex)

    // Kotlin
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.kotlinx.coroutines.android)

    // Support
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.vectordrawable)
    implementation(libs.browser)
    implementation(libs.percentlayout)
    implementation(libs.cardview)
    implementation(libs.emoji)
    implementation(libs.constraintlayout)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-auth")

    implementation(libs.play.services.auth)
    implementation(libs.lifecycle.runtime.ktx)

    // Image management
    implementation(libs.glide.runtime)
    add("annotationProcessor", libs.glide.compiler)

    // Used for tags
    implementation(libs.layouts)
}
