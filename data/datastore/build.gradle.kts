import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import kotlin.collections.plusAssign

plugins {
    alias(libs.plugins.kotlin.multiplatform.android.library)
    alias(libs.plugins.kotlin.multiplatform)

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
