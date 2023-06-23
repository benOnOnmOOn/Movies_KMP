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
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            apply(plugin = "kotlin-kapt")
            dependencies {

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

dependencyAnalysis {
    // resolve false positive problems caused by using kotlin multiplatform
    issues {
        onUnusedAnnotationProcessors {
            exclude(
                "com.google.dagger:dagger-compiler:2.46.1",
                "com.google.dagger:hilt-android-compiler:2.46.1"
            )
        }
        onRedundantPlugins { exclude("kotlin-kapt") }
        onUsedTransitiveDependencies {
            exclude(
                "com.google.dagger:dagger:2.46.1",
                "javax.inject:javax.inject:1",
                "com.google.dagger:hilt-core:2.46.1"
            )
        }
    }

}

android { namespace = "com.bz.movies.kmp.network" }