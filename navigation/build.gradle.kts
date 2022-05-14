import Dependencies.AndroidX
import Dependencies.DI
import Dependencies.View
import ProjectLib.characterDetail
import ProjectLib.characterSearch
import ProjectLib.core

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    parcelize
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    defaultConfig {
        compileSdk = Config.Version.compileSdkVersion
        minSdk = Config.Version.minSdkVersion
        targetSdk = Config.Version.targetSdkVersion
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
//            versionNameSuffix(BuildTypeDebug.versionNameSuffix)
        }
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(project(core))
    implementation(project(characterSearch))
    implementation(project(characterDetail))

    implementation(DI.daggerHiltAndroid)
    implementation(View.fragment)
    implementation(AndroidX.navigationFragmentKtx)

    kapt(DI.AnnotationProcessor.daggerHilt)
}
