plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas/")
}

kotlin {
    androidTarget {
        dependencies {
            api(project(":data:dto"))
            api(libs.kotlin.stdlib)

            lintChecks(libs.slack.lint.checks)

            implementation(libs.androidx.sqlite)
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
                implementation(libs.koin.android)
                implementation(libs.androidx.sqlite)
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

android {
    namespace = "com.bz.movies.kmp.roomdb"
}
