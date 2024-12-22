plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinx.kover)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

kotlin {
    androidTarget {
        dependencies {
            lintChecks(libs.slack.lint.checks)

            api(project(":data:dto"))
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.core)
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.android)
            testImplementation(libs.junit.jupiter.api)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":data:dto"))
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.core)
            implementation(libs.androidx.core.ktx)

            implementation(libs.koin.android)

            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.http)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.utils)

            implementation(libs.slf4j.android)
        }

        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.junit.jupiter.api)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.network"
}
