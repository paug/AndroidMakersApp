buildscript {
    repositories {
        google()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha09")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("io.fabric.tools:gradle:1.31.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.ar.sceneform:plugin:1.15.0")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
    }
}
