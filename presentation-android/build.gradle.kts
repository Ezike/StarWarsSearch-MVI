import Dependencies.AndroidX
import Dependencies.View
import ProjectLib.presentation

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
}

android {
    namespace = "com.ezike.tobenna.starwarssearch.presentation_android"
    compileSdkVersion(Config.Version.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
//            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }
}

dependencies {
    implementation(project(presentation))
    implementation(AndroidX.viewModel)
    implementation(AndroidX.lifeCycleCommon)
    implementation(View.fragment)
}
