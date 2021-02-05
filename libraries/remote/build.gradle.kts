import Dependencies.DI
import Dependencies.Network

plugins {
    kotlinLibrary
    kotlin(kotlinKapt)
}

dependencies {
    implementAll(Network.components)
    implementation(DI.hiltCore)
    kapt(DI.AnnotationProcessor.daggerHilt)
}
