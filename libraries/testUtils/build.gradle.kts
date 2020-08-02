import Dependencies.Test
import ProjectLib.domain

plugins {
    kotlinLibrary
}

dependencies {
    implementation(project(domain))
    api(Test.junit)
    api(Test.truth)
    api(Test.coroutinesTest)
}
