import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.View
import ProjectLib.core
import ProjectLib.libCharacterSearch
import ProjectLib.presentation
import ProjectLib.presentationAndroid
import ProjectLib.testUtils

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
    implementation(project(presentation))
    implementation(project(presentationAndroid))
    implementation(project(libCharacterSearch))

    testImplementation(project(testUtils))
    androidTestImplementation(project(testUtils))

    with(View) {
        implementAll(components)
        implementation(fragment)
        implementation(materialComponent)
        implementation(constraintLayout)
        implementation(cardView)
        implementation(recyclerView)
        implementation(shimmerLayout)
    }

    implementation(DI.daggerHiltAndroid)
    implementation(DI.hiltViewModel)
    implementAll(AndroidX.components)
    implementAll(Coroutines.components)

    kapt(DI.AnnotationProcessor.daggerHilt)
    kapt(DI.AnnotationProcessor.androidxHiltCompiler)
}
