
import Dependencies.AndroidX
import Dependencies.DI
import Dependencies.Network
import Dependencies.Performance
import Dependencies.View
import ProjectLib.cache
import ProjectLib.characterDetail
import ProjectLib.characterSearch
import ProjectLib.core
import ProjectLib.navigation
import ProjectLib.presentation
import ProjectLib.presentationAndroid
import ProjectLib.remote

plugins {
    androidApplication
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    namespace = "com.ezike.tobenna.starwarssearch"
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdk = Config.Version.minSdkVersion
        compileSdk = Config.Version.compileSdkVersion
        targetSdk = Config.Version.targetSdkVersion
        versionCode = Config.Version.versionCode
        versionName = Config.Version.versionName
        multiDexEnabled = Config.isMultiDexEnabled
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(characterSearch))
    implementation(project(characterDetail))
    implementation(project(cache))
    implementation(project(presentation))
    implementation(project(presentationAndroid))
    implementation(project(remote))
    implementation(project(core))
    implementation(project(navigation))

    debugImplementation(Performance.leakCanary)

    implementAll(View.components)
    implementation(Network.moshi)
    implementation(DI.daggerHiltAndroid)
    implementation(View.fragment)

    AndroidX.run {
        implementation(activity)
        implementation(coreKtx)
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
        implementation(multiDex)
    }

    kapt(DI.AnnotationProcessor.daggerHilt)
}
