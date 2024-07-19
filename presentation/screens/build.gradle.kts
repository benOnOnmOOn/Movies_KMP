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

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("org.koin.core.annotation.KoinExperimentalAPI")
        }
        commonMain {
            dependencies {
                api(project(":data:network"))
                api(project(":data:database"))
                api(project(":data:dto"))

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

                implementation(libs.kermit)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
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
    namespace = "com.bz.presentation.screens"

    buildFeatures {
        compose = true
    }
    dependencies {
        api(project(":data:database"))
        api(project(":data:dto"))
        api(project(":data:network"))
        api(libs.androidx.animation)
        api(libs.androidx.runtime)
        api(libs.androidx.ui)
        api(libs.androidx.compose.material3)
        api(libs.androidx.navigation.common)
        api(libs.androidx.navigation.runtime)
        api(libs.kotlin.stdlib)

        implementation(libs.androidx.animation.core)
        implementation(libs.androidx.foundation.layout)
        implementation(libs.androidx.foundation)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.text)
        implementation(libs.androidx.ui.unit)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.navigation.compose)

        lintChecks(libs.slack.lint.checks)
        lintChecks(libs.compose.lint.checks)

        debugImplementation(libs.kermit.android.debug)
        debugImplementation(libs.kermit.core.android.debug)
        releaseImplementation(libs.kermit.core)
    }
}
