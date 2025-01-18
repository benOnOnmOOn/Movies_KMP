import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.kotlin.multiplatform)

    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.android.library)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {
    androidTarget {
        dependencies {
            api(libs.kotlin.stdlib)

            lintChecks(libs.slack.lint.checks)

            implementation(libs.androidx.datastore)
            implementation(libs.koin.android)

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
                implementation(libs.androidx.datastore.core)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.datastore.preferences.core)

                implementation(libs.kermit)

                implementation(libs.koin.core)
                implementation(libs.koin.core.coroutines)

                implementation(libs.kotlinx.coroutines.core)
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
                implementation(libs.koin.android)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}
