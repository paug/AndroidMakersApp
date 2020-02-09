buildscript {
    repositories {
        google()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("io.fabric.tools:gradle:1.28.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.ar.sceneform:plugin:1.7.0")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
    }
}
