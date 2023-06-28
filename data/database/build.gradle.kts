plugins {
    kotlin("multiplatform") version "1.8.21"
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0-rc01"
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    android()

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
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-rc01")
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
                implementation("app.cash.sqldelight:android-driver:2.0.0-rc01")
                implementation(libs.io.insert.koin.android)
            }
        }

        @Suppress("UnusedPrivateProperty")
        val iosMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0-rc01")
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
    lint {
    this.checkDependencies = false
    }
}