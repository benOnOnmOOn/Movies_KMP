import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.kover)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

dependencyAnalysis {
    issues {
        onUnusedDependencies { exclude(libs.ui.tooling.preview) }
    }
}

kotlin {
    androidTarget {
        dependencies {
            implementation(project(":data:room"))
            implementation(project(":data:datastore"))
            implementation(project(":data:dto"))
            implementation(project(":data:network"))

            lintChecks(libs.slack.lint.checks)
            lintChecks(libs.compose.lint.checks)

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
            debugImplementation(libs.ui.tooling.preview)
            releaseImplementation(libs.kermit.core)
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
            implementation(project(":data:network"))
            implementation(project(":data:room"))
            implementation(project(":data:datastore"))
            implementation(project(":data:dto"))

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
            implementation(libs.koin.core.viewmodel)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.material3)
            implementation(libs.navigation.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(project(":data:room"))
            implementation(project(":data:datastore"))
            implementation(project(":data:dto"))
            implementation(project(":data:network"))

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

android {
    namespace = "com.bz.presentation.screens"

    buildFeatures {
        compose = true
    }
}

afterEvaluate {
    tasks.named("explodeCodeSourceDebug").dependsOn("generateActualResourceCollectorsForAndroidMain")
    tasks.named("explodeCodeSourceRelease").dependsOn("generateActualResourceCollectorsForAndroidMain")
}
