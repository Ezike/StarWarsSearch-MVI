import Dependencies.DI
import ProjectLib.domain
import ProjectLib.testUtils

plugins {
    kotlinLibrary
    kotlin(kotlinKapt)
}

dependencies {
    implementation(project(domain))
    testImplementation(project(testUtils))
    implementation(DI.hiltCore)
    kapt(DI.AnnotationProcessor.daggerHilt)
}
