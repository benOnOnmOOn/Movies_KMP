import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.android.library)
    alias(libs.plugins.sqldelight)

    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {

    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "com.bz.movies.kmp.database"
        compileSdk = 35
        buildToolsVersion = "35.0.0"
        minSdk = 27

        compilations.all {
            compilerOptions.configure { jvmTarget = JvmTarget.JVM_21 }
        }

        lint {
            baseline = project.file("lint-baseline.xml")
            disable +=
                listOf(
                    "NewerVersionAvailable",
                    "GradleDependency",
                    "ObsoleteLintCustomCheck",
                )
            abortOnError = true
            checkAllWarnings = true
            warningsAsErrors = true
            checkReleaseBuilds = false
            checkDependencies = false
        }

        packaging.resources.excludes +=
            setOf(
                "kotlin/**",
                "META-INF/**",
                "**.properties",
                "kotlin-tooling-metadata.json",
                "DebugProbesKt.bin",
            )
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
