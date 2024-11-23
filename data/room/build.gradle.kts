plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
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

ksp {
    arg("room.generateKotlin", "true")
}

kotlin {
    androidTarget {
        dependencies {
            api(project(":data:dto"))
            api(libs.kotlin.stdlib)

            lintChecks(libs.slack.lint.checks)

            implementation(libs.androidx.sqlite)
            implementation(libs.io.insert.koin.android)

            testImplementation(libs.org.junit.jupiter.api)
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
                implementation(libs.io.insert.koin.android)
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
    dependencies {
        add("kspAndroid", libs.androidx.room.compiler)
        add("kspIosSimulatorArm64", libs.androidx.room.compiler)
        add("kspIosX64", libs.androidx.room.compiler)
        add("kspIosArm64", libs.androidx.room.compiler)
    }
}

android {
    namespace = "com.bz.movies.kmp.roomdb"
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

