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

    testImplementation(Test.mockWebServer)

    kapt(Network.AnnotationProcessor.moshi)
}
