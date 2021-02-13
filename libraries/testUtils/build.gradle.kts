import Dependencies.Test

plugins {
    kotlinLibrary
}

dependencies {
    api(Test.junit)
    api(Test.truth)
    api(Test.coroutinesTest)
}
