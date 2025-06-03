import com.autonomousapps.DependencyAnalysisSubExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)

    alias(libs.plugins.movies.android.library)
    alias(libs.plugins.movies.android.library.compose)
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
    androidTarget {
        dependencies {
            implementation(projects.data.room)
            implementation(projects.data.datastore)
            implementation(projects.data.dto)
            implementation(projects.data.network)

            lintChecks(libs.lint.slack.checks)
            lintChecks(libs.lint.compose.checks)

            api(libs.androidx.navigation.common)
            api(libs.androidx.navigation.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.lifecycle.common)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)

            debugImplementation(libs.androidx.compose.ui.tooling)
            debugImplementation(libs.androidx.compose.ui.tooling.preview)
            debugImplementation(libs.kermit.android.debug)
            debugImplementation(libs.kermit.core.android.debug)
            debugImplementation(libs.kotlinx.compose.ui.tooling.preview)
            releaseImplementation(libs.kermit.core)
            testImplementation(libs.junit.jupiter.api)

        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("org.koin.core.annotation.KoinExperimentalAPI")
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
            implementation(libs.kotlinx.datetime)

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

            api(libs.androidx.compose.animation)
            api(libs.androidx.compose.material3)
            api(libs.androidx.compose.runtime)
            api(libs.androidx.compose.ui)
            api(libs.androidx.navigation.common)
            api(libs.androidx.navigation.runtime)

            api(libs.kotlin.stdlib)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.foundation.layout)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.text)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.ui.unit)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.kotlinx.coroutines.android)
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
