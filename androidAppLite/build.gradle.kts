plugins {
    alias(libs.plugins.dexcount)

    alias(libs.plugins.movies.android.application)
    alias(libs.plugins.movies.android.application.compose)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

android {
    namespace = "com.bz.movies.kmp.android"
}

dependencies {
    lintChecks(libs.lint.slack.checks)

    implementation(projects.presentation.core)
    implementation(projects.presentation.screens)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)

    implementation(libs.androidx.startup.runtime)

    implementation(libs.kermit)
    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)
    releaseImplementation(libs.kermit.core)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.jupiter.engine)
    //noinspection UseTomlInstead
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling.android)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestRuntimeOnly(libs.junit.jupiter.engine)
}
configurations.releaseImplementation {
    exclude("io.ktor", "ktor-websocket-serialization-jvm")
    exclude("io.ktor", "ktor-websocket-serialization")
    exclude("io.ktor", "ktor-websockets-jvm")
    exclude("io.ktor", "ktor-websockets")
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("releaseRuntimeClasspath")
}
