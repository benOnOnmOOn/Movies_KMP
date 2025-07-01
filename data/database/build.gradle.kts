plugins {
    alias(libs.plugins.sqldelight)

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
                api(projects.data.dto)
                implementation(libs.androidx.sqlite)
                implementation(libs.sqldelight.android.driver)
                implementation(libs.koin.android)
//                lintChecks(libs.slack.lint.checks)
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
