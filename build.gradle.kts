buildscript {
    repositories {
        google()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-beta04")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("io.fabric.tools:gradle:1.31.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        // Remove when the open
        maven {
            url = uri("https://dl.bintray.com/openfeedback/Android/")
        }
    }
}
