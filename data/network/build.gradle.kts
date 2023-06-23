import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("kapt") apply false
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
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                apply(plugin = "kotlin-kapt")

                // HILT
                configurations.getByName("kapt").dependencies.apply {
                    add(libs.com.google.hilt.android.compiler.get())
                    add(libs.com.google.dagger.compiler.get())
                }
                implementation(libs.com.google.hilt.android)
                implementation(libs.com.google.hilt.core)
                api(libs.com.google.dagger)
                api(libs.javax.inject)
                //
                implementation(libs.org.jetbrains.kotlinx.coroutines.android)
                implementation(libs.io.ktor.client.android)
                implementation(libs.io.ktor.client.okhttp)
            }
        }

        val iosMain by getting {

            dependencies {
                implementation(libs.io.ktor.client.ios)
            }
        }
    }
}

android { namespace = "com.bz.movies.kmp.network" }