import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.app.cash.sqldelight)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "database"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.io.insert.koin.core)
                api(project(":data:dto"))
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

tasks {
    runKtlintCheckOverCommonMainSourceSet {
        doFirst {
            source.forEach { println(it) }
        }
    }
}

configure<KtlintExtension> {
    android.set(true)
    enableExperimentalRules.set(true)
    filter {
        exclude { element -> element.file.path.contains("generated/") }
        exclude { element -> element.file.path.contains("build/") }
    }
}

sqldelight {
    databases {
        create("MoviesDB2") {
            packageName.set("com.bz.movies.kmp.database")
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.database"
}
