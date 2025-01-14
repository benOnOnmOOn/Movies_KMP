import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import kotlin.collections.plusAssign

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.android.library)    alias(libs.plugins.movies.android.lint)

    alias(libs.plugins.movies.android.room)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {
    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "com.bz.movies.kmp.roomdb"
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
