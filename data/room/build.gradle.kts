plugins {
    alias(libs.plugins.kotlin.multiplatform)

    alias(libs.plugins.movies.android.library)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.android.room)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {
    androidTarget {
        dependencies {
            api(project(":data:dto"))
            api(libs.kotlin.stdlib)

            lintChecks(libs.lint.slack.checks)

            implementation(libs.androidx.sqlite)
            implementation(libs.koin.android)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":data:dto"))
                implementation(libs.koin.core)
                implementation(libs.koin.core.coroutines)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.room.common)
                implementation(libs.androidx.room.runtime)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                api(project(":data:dto"))
                implementation(libs.koin.android)
                implementation(libs.androidx.sqlite)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}
