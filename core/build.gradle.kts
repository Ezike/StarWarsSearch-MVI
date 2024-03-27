import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.Network
import Dependencies.View

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    namespace = "com.ezike.tobenna.starwarssearch.core"
    compileSdkVersion(Config.Version.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
//            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }
}

dependencies {
    implementation(AndroidX.lifeCycleCommon)
    implementation(View.appCompat)
    implementation(View.materialComponent)
    implementation(View.fragment)
    implementation(DI.daggerHiltAndroid)
    implementation(Network.moshi)
    implementation(Coroutines.core)

    kapt(DI.AnnotationProcessor.daggerHilt)
}
