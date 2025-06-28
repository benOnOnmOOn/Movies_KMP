import com.autonomousapps.DependencyAnalysisSubExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.jetbrains.compose)

    alias(libs.plugins.movies.kotlin.android.library)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

extensions.findByType<DependencyAnalysisSubExtension>()?.apply {
    issues {
        onUnusedDependencies { exclude(libs.kotlinx.compose.ui.tooling.preview) }
    }
}

kotlin {

    androidLibrary {
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn(
                "org.koin.core.annotation.KoinExperimentalAPI",
            )
            languageSettings.optIn(
                "kotlin.time.ExperimentalTime",
            )
        }

        commonMain.dependencies {
            implementation(projects.data.network)
            implementation(projects.data.room)
            implementation(projects.data.datastore)
            implementation(projects.data.dto)

            implementation(compose.components.resources)

            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(libs.coil)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.core)
            implementation(libs.coil.network.ktor)

            implementation(libs.kermit)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viemodel)
            implementation(libs.koin.core)
            implementation(libs.koin.core.coroutines)
            implementation(libs.koin.core.viewmodel)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlinx.compose.material3)
            implementation(libs.kotlinx.compose.navigation)
            implementation(libs.kotlinx.lifecycle.viewmodel.savedstate)
            implementation(libs.kotlinx.savedstate)
            implementation(libs.kotlinx.compose.lifecycle.runtime.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(projects.data.room)
            implementation(projects.data.datastore)
            implementation(projects.data.dto)
            implementation(projects.data.network)

            api(libs.androidx.navigation.common)
            api(libs.androidx.navigation.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.lifecycle.common)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.savedstate)
        }

        iosMain.dependencies {
        }
    }
}

afterEvaluate {
    tasks.findByName("explodeCodeSourceDebug")
        ?.dependsOn("generateActualResourceCollectorsForAndroidMain")
    tasks.findByName("explodeCodeSourceRelease")
        ?.dependsOn("generateActualResourceCollectorsForAndroidMain")
}
