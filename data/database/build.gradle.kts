import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.app.cash.sqldelight)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "database"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.io.insert.koin.core)
                api(project(":data:dto"))
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.app.cash.sqldelight.coroutines.extensions)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        @Suppress("UnusedPrivateProperty")
        val androidMain by getting {
            dependencies {
                implementation(libs.app.cash.sqldelight.android.driver)
                implementation(libs.io.insert.koin.android)
            }
        }

        @Suppress("UnusedPrivateProperty")
        val iosMain by getting {
            dependencies {
                implementation(libs.app.cash.sqldelight.native.driver)
            }
        }
    }
}

sqldelight {
    databases {
        create("MoviesDB2") {
            packageName.set("com.bz.movies.kmp.database")
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.database"
}
