plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":data:dto"))
                implementation(libs.io.ktor.client.core)
                implementation(libs.io.ktor.serialization.content.negotiation)
                implementation(libs.io.ktor.serialization.kotlinx.json)
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.io.insert.koin.core)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.androidx.core.ktx)
                implementation(libs.org.jetbrains.kotlinx.coroutines.android)
                implementation(libs.io.ktor.client.android)
                implementation(libs.io.insert.koin.android)
                implementation(libs.ktor.client.logging)
                implementation(libs.slf4j.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.io.ktor.client.ios)
            }
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.network"
    dependencies {
        api(project(":data:dto"))

        implementation(libs.io.ktor.client.android)
        api(libs.kotlin.stdlib)

        implementation(libs.io.ktor.http)
        implementation(libs.io.ktor.serialization)
        implementation(libs.io.ktor.utils)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.androidx.core)
        implementation(libs.androidx.core.ktx)
        implementation(libs.io.insert.koin.android)

        lintChecks(libs.slack.lint.checks)

        testImplementation(libs.kotlin.test)
    }
}
