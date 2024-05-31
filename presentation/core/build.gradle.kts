import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

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
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "core"
            linkerOpts.add("-lsqlite3")
            xcf.add(this)
            binaryOption("bundleId", "com.bz.movies.kmp")
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
                implementation(libs.app.cash.sqldelight.native.driver)
            }
        }
    }
}

android {
    namespace = "com.bz.core"

    buildFeatures {
        compose = true
    }
    dependencies {
        implementation(project(":data:database"))
        implementation(project(":data:network"))
        implementation(project(":presentation:screens"))

        api(libs.androidx.activity)
        api(libs.androidx.runtime)
        api(libs.kotlin.stdlib)

        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.foundation)
        implementation(libs.androidx.foundation.layout)
        implementation(libs.androidx.navigation.common)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.navigation.runtime)
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
    }
}
