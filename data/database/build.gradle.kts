plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.app.cash.sqldelight)
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
            api(project(":data:dto"))
            api(libs.kotlin.stdlib)

            lintChecks(libs.slack.lint.checks)
            implementation(libs.androidx.sqlite)
            implementation(libs.app.cash.sqldelight.android.driver)
            implementation(libs.io.insert.koin.android)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":data:dto"))
                implementation(libs.io.insert.koin.core)
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.app.cash.sqldelight.coroutines.extensions)
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
                implementation(libs.app.cash.sqldelight.android.driver)
                implementation(libs.io.insert.koin.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.app.cash.sqldelight.native.driver)
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

android {
    namespace = "com.bz.movies.kmp.database"
}
