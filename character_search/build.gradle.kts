import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.FlowBinding
import Dependencies.Test
import Dependencies.View
import ProjectLib.core
import ProjectLib.data
import ProjectLib.domain
import ProjectLib.presentation
import ProjectLib.presentation_android
import ProjectLib.remote
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
        compileSdkVersion(Config.Version.compileSdkVersion)
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
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
    implementation(project(presentation_android))
    implementation(project(domain))

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

    implementation(FlowBinding.android)
    implementation(DI.daggerHiltAndroid)
    implementation(DI.hiltViewModel)
    implementAll(AndroidX.components)
    implementAll(Coroutines.components)

    kapt(DI.AnnotationProcessor.daggerHilt)
    kapt(DI.AnnotationProcessor.jetpackHiltCompiler)

    androidTestImplementation(project(data))
    androidTestImplementation(project(remote))
    androidTestImplementation(DI.hiltTesting)
    androidTestImplementation(Test.espresso)
    androidTestImplementation(Test.espressoContrib)
    androidTestImplementation(Test.fragmentTesting)
    androidTestImplementation(Test.rules)
    androidTestImplementation(Test.archCoreTest)

    androidTestImplementation(Test.runner)
    androidTestImplementation(Test.androidXTest)

    kaptAndroidTest(DI.AnnotationProcessor.daggerHilt)
    kaptAndroidTest(DI.AnnotationProcessor.jetpackHiltCompiler)
}
