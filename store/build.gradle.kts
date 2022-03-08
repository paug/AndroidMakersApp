plugins {
    id("fr.androidmakers.gradle.android.library")
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(libs.kotlinx.coroutines.android)
}
