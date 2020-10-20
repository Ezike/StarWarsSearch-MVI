import Dependencies.DI
import Dependencies.Network
import Dependencies.Test
import ProjectLib.data
import ProjectLib.testUtils

plugins {
    kotlinLibrary
    kotlin(kotlinKapt)
}

dependencies {
    implementation(project(data))
    testImplementation(project(testUtils))

    implementAll(Network.components)
    implementation(DI.hiltCore)

    testImplementation(Test.mockWebServer)

    kapt(Network.AnnotationProcessor.moshi)
    kapt(DI.AnnotationProcessor.daggerHilt)
}
