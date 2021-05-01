import Dependencies.Cache
import Dependencies.DI
import Dependencies.Network
import ProjectLib.cache

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    compileSdk = Config.Version.compileSdkVersion
    defaultConfig {
        minSdk = Config.Version.minSdkVersion
        targetSdk = Config.Version.targetSdkVersion

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += Pair("room.incremental", "true")
            }
        }
        buildConfigField("int", "databaseVersion", 1.toString())
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }
}

dependencies {
    testImplementation(project(cache))

    implementation(DI.daggerHiltAndroid)
    implementation(Network.moshi)
    implementation(Cache.room)

    kapt(Cache.AnnotationProcessor.room)
    kapt(DI.AnnotationProcessor.daggerHilt)
}
