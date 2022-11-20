import Dependencies.Cache
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.Network
import Dependencies.Test
import ProjectLib.cache
import ProjectLib.remote
import ProjectLib.testUtils

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    namespace = "com.ezike.tobenna.starwarssearch.libcharactersearch"
    compileSdk = Config.Version.compileSdkVersion
    defaultConfig {
        buildFeatures.buildConfig = true
        buildConfigField("String", "BASE_URL", "\"https://swapi.dev/api/\"")
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }
}

dependencies {
    implementation(project(remote))
    implementation(project(cache))
    implementation(Coroutines.core)
    implementation(Network.retrofit)

    implementation(DI.daggerHiltAndroid)
    kapt(DI.AnnotationProcessor.daggerHilt)

    testImplementation(Network.moshi)
    testImplementation(Network.retrofitMoshi)
    testImplementation(Cache.room)

    testImplementation(Test.runner)
    testImplementation(Test.androidXTest)
    testImplementation(Test.robolectric)

    testImplementation(project(testUtils))
    testImplementation(Test.junit)
    testImplementation(Test.truth)
    testImplementation(Test.coroutinesTest)
    testImplementation(Test.mockWebServer)
}
