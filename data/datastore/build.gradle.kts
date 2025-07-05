plugins {
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.kmp.library)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
        commonMain {
            dependencies {
                implementation(projects.data.dto)
                implementation(libs.androidx.datastore.core)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.datastore.preferences.core)

                implementation(libs.kermit)

                implementation(libs.koin.core)
                implementation(libs.koin.core.coroutines)

                implementation(libs.kotlinx.coroutines.core)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain {
            dependencies {
                api(libs.kotlin.stdlib)

//                lintChecks(libs.slack.lint.checks)

                implementation(libs.androidx.datastore)
                implementation(libs.koin.android)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}
