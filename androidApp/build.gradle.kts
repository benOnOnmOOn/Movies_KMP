plugins {
    alias(libs.plugins.dexcount)

    alias(libs.plugins.movies.android.application)
    alias(libs.plugins.movies.android.application.compose)
    alias(libs.plugins.movies.android.application.firebase)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

android {
    namespace = "com.bz.movies.kmp.android"
}

dependencies {
    implementation(project(":presentation:core"))
    implementation(project(":presentation:screens"))
    val enableKover =
        providers.gradleProperty("movies.enableKover").getOrElse("false").toBoolean()

    if (enableKover) {
        add("kover", project(":presentation:core"))
        add("kover", project(":presentation:screens"))
        add("kover", project(":data:database"))
        add("kover", project(":data:network"))
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

    compileOnly(libs.firebase.google.services)

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
