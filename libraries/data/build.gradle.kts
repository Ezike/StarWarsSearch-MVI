import ProjectLib.domain
import ProjectLib.testUtils

plugins {
    kotlinLibrary
}

dependencies {
    implementation(project(domain))
    testImplementation(project(testUtils))
}
