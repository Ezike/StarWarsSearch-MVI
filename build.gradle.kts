import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

allprojects {
    repositories.applyDefault()
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(kotlinVersion)
            }
        }
    }
}

subprojects {
    applySpotless
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_11.toString()
            freeCompilerArgs += "-Xuse-experimental=" +
                "kotlin.Experimental," +
                "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                "kotlinx.coroutines.InternalCoroutinesApi," +
                "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                "kotlinx.coroutines.FlowPreview"
            freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
        }
    }
}
