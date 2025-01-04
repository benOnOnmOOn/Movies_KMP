import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.kotlin.multiplatform)
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

android {
    namespace = "com.bz.movies.kmp.database"
}
