import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
    androidTarget {
        dependencies {
            api(libs.kotlin.stdlib)

            lintChecks(libs.slack.lint.checks)

            implementation(libs.androidx.datastore)
            implementation(libs.io.insert.koin.android)

            debugImplementation(libs.kermit.android.debug)
            debugImplementation(libs.kermit.core.android.debug)

            releaseImplementation(libs.kermit.core)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":data:dto"))
                implementation(libs.io.insert.koin.core)
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.androidx.datastore.core)
                implementation(libs.kermit)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.datastore.preferences.core)
                implementation(libs.kotlinx.datetime)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.io.insert.koin.android)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.database"
}
