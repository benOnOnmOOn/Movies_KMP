plugins {
    alias(libs.plugins.movies.android.room)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.kmp.library)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                api(projects.data.dto)
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
                api(projects.data.dto)
                api(libs.kotlin.stdlib)

                implementation(libs.koin.android)
                implementation(libs.androidx.sqlite)
//                lintChecks(libs.slack.lint.checks)
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
