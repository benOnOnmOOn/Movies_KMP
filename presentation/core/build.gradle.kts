plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "core"
        }
    }


    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":presentation:screens"))
                implementation(project(":data:network"))
                implementation(project(":data:database"))

                implementation(compose.components.resources)
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.material3)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.navigation.compose)
                implementation(libs.coil.core)
                implementation(libs.coil.compose.core)
                implementation(libs.coil)
                implementation(libs.coil.network.ktor)
                implementation(libs.io.insert.koin.core)
                implementation(libs.io.insert.koin.compose)
                implementation(libs.io.insert.koin.compose.viemodel)
            }
        }


        androidMain {
            dependencies {
                implementation(libs.org.jetbrains.kotlinx.coroutines.android)
                implementation(libs.androidx.compose.ui.tooling.preview)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.bz.core"

    buildFeatures {
        compose = true
    }
}