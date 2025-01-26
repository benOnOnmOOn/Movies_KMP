plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.sqldelight)

    alias(libs.plugins.movies.android.library)
    alias(libs.plugins.movies.android.lint)
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
            implementation(libs.sqldelight.android.driver)
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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.sqldelight.coroutines.extensions)
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
                implementation(libs.androidx.sqlite)
                implementation(libs.sqldelight.android.driver)
                implementation(libs.koin.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

sqldelight {
    databases {
        create("MoviesDB2") {
            packageName.set("com.bz.movies.kmp.database")
        }
    }
    linkSqlite = true
}
