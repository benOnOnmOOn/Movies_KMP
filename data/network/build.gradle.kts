import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

kotlin {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "network"
        }
    }

    sourceSets.forEach {
        it.dependencies {
            implementation(project.dependencies.enforcedPlatform(libs.io.ktor.bom))
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":data:dto"))
                implementation(libs.io.ktor.client.core)
                implementation(libs.io.ktor.serialization.content.negotiation)
                implementation(libs.io.ktor.serialization.kotlinx.json)
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.io.insert.koin.core)
                implementation(libs.androidx.core)
                implementation(libs.androidx.core.ktx)
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

                implementation(libs.org.jetbrains.kotlinx.coroutines.android)
                implementation(libs.io.ktor.client.android)
                implementation(libs.io.ktor.client.okhttp)

                // use debug impl to prevent from adding this deps to release version

            }
        }

        @Suppress("UnusedPrivateProperty")
        val iosMain by getting {

            dependencies {
                implementation(libs.io.ktor.client.ios)
            }
        }
    }
}


android {
    namespace = "com.bz.movies.kmp.network"
    dependencies {
        debugApi(libs.com.squareup.okhttp.logging.interceptor)
    }
}