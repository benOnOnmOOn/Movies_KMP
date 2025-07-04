plugins {
    alias(libs.plugins.dexcount)

    alias(libs.plugins.movies.android.application)
    alias(libs.plugins.movies.android.application.firebase)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

android {
    namespace = "com.bz.movies.kmp.android"
}

dependencies {
    implementation(projects.presentation.core)
    implementation(projects.presentation.screens)
    val enableKover =
        project.findProperty("movies.enableKover")?.toString().toBoolean()

    if (enableKover) {
        add("kover", projects.presentation.core)
        add("kover", projects.presentation.screens)
        add("kover", projects.data.database)
        add("kover", projects.data.network)
    }

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(libs.koin.android)

    implementation(libs.androidx.startup.runtime)

    implementation(libs.kermit)
    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)
    releaseImplementation(libs.kermit.crashlytics)
    releaseImplementation(libs.kermit.core)

    lintChecks(libs.lint.slack.checks)

    releaseImplementation(libs.firebase.analytics.ktx)
    releaseImplementation(libs.firebase.crashlytics.ktx)
    releaseImplementation(libs.firebase.perf)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.jupiter.engine)
    //noinspection UseTomlInstead
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly(libs.firebase.google.services)

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
