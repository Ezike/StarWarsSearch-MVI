import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.FlowBinding
import Dependencies.View
import ProjectLib.core
import ProjectLib.domain
import ProjectLib.presentation
import ProjectLib.testUtils

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    id("kotlin-android")
    daggerHilt
}

android {
    defaultConfig {
        compileSdkVersion(Config.Version.compileSdkVersion)
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }
}

dependencies {
    implementation(project(core))
    implementation(project(presentation))
    implementation(project(domain))

    testImplementation(project(testUtils))

    with(View) {
        implementAll(components)
        implementation(fragment)
        implementation(materialComponent)
        implementation(constraintLayout)
        implementation(cardView)
        implementation(recyclerView)
        implementation(recyclerViewAnimator)
        implementation(shimmerLayout)
    }

    implementation(FlowBinding.android)
    implementation(DI.daggerHiltAndroid)
    implementation(DI.hiltViewModel)
    implementAll(AndroidX.components)
    implementAll(Coroutines.components)

    kapt(DI.AnnotationProcessor.daggerHiltAndroid)
    kapt(DI.AnnotationProcessor.hiltCompiler)
}
