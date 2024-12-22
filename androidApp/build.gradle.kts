import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsPlugin
import com.google.gms.googleservices.GoogleServicesPlugin

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.dexcount)
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.kover)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }

    reports {
        variant("custom") {
            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }
        filters {
            excludes {
                annotatedBy(
                    "*Generated*",
                    "*Composable*",
                )
            }
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.android"

    defaultConfig {
        applicationId = "com.bz.movies.kmp"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            apply<GoogleServicesPlugin>()
            apply<CrashlyticsPlugin>()
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":presentation:core"))
    implementation(project(":presentation:screens"))

    kover(project(":presentation:core"))
    kover(project(":presentation:screens"))
    kover(project(":data:database"))
    kover(project(":data:network"))
    kover(project(":data:dto"))

    implementation(libs.kotlin.stdlib)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.androidx.startup.runtime)

    implementation(libs.kermit)
    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)
    releaseImplementation(libs.kermit.crashlytics)
    releaseImplementation(libs.kermit.core)

    lintChecks(libs.slack.lint.checks)

    releaseImplementation(libs.firebase.analytics.ktx)
    releaseImplementation(libs.firebase.crashlytics.ktx)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.jupiter.engine)

    compileOnly(libs.google.services)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling.android)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestRuntimeOnly(libs.junit.jupiter.engine)
}

// configurations.releaseImplementation {
//    exclude("org.slf4j", "slf4j-api")
//    exclude("org.slf4j", "slf4j-android")
//    exclude("io.ktor", "ktor-client-logging-jvm")
//    exclude("io.ktor", "ktor-client-logging")
// }

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("releaseRuntimeClasspath")
}
