plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
    alias(libs.plugins.kotlin.serialization)
}

group = "com.bz.movies"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.io.ktor.http)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.io.ktor.serialization.kotlinx.json)
    implementation(libs.io.ktor.serialization)
    implementation(libs.io.ktor.utils)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    runtimeOnly(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("runtimeClasspath")
}
