import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
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
            implementation(project(":data:database"))
            implementation(project(":data:dto"))
            implementation(project(":data:network"))

            lintChecks(libs.slack.lint.checks)
            lintChecks(libs.compose.lint.checks)

            api(libs.androidx.navigation.common)
            api(libs.androidx.navigation.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.common)
            implementation(libs.androidx.lifecycle.runtime.compose)

            debugImplementation(libs.androidx.compose.ui.tooling.preview)
            debugImplementation(libs.ui.tooling.preview)
            debugImplementation(libs.androidx.ui.tooling)
            debugImplementation(libs.kermit.android.debug)
            debugImplementation(libs.kermit.core.android.debug)
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
            implementation(project(":data:database"))
            implementation(project(":data:dto"))

            implementation(compose.components.resources)
            implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.material3)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.compose.core)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.io.insert.koin.core)
            implementation(libs.io.insert.koin.core.viewmodel)
            implementation(libs.io.insert.koin.compose)
            implementation(libs.io.insert.koin.compose.viemodel)

            implementation(libs.kermit)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(project(":data:database"))
            implementation(project(":data:dto"))
            implementation(project(":data:network"))

            api(libs.androidx.animation)
            api(libs.androidx.runtime)
            api(libs.androidx.ui)
            api(libs.androidx.compose.material3)
            api(libs.androidx.navigation.common)
            api(libs.androidx.navigation.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.foundation.layout)
            implementation(libs.androidx.foundation)
            implementation(libs.androidx.ui.graphics)
            implementation(libs.androidx.ui.text)
            implementation(libs.androidx.ui.unit)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.org.jetbrains.kotlinx.coroutines.android)
            implementation(libs.androidx.compose.ui.tooling.preview)
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
