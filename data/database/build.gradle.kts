plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.sqldelight)
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

android {
    namespace = "com.bz.movies.kmp.database"
}
