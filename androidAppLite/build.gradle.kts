plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.dexcount)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
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
    lintChecks(libs.slack.lint.checks)

    implementation(project(":presentation:core"))
    implementation(project(":presentation:screens"))

    implementation(libs.kotlin.stdlib)

    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.android)

    implementation(libs.androidx.startup.runtime)

    implementation(libs.kermit)
    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)
    releaseImplementation(libs.kermit.core)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)
}
configurations.releaseImplementation {
    exclude("org.slf4j", "slf4j-android")
    exclude("org.slf4j", "slf4j-api")
    exclude("io.ktor", "ktor-websocket-serialization-jvm")
    exclude("io.ktor", "ktor-websocket-serialization")
    exclude("io.ktor", "ktor-websockets-jvm")
    exclude("io.ktor", "ktor-websockets")
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("releaseRuntimeClasspath")
}
