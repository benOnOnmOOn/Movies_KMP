plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    alias(libs.plugins.dependency.guard)
}

group = "com.bz.movies"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.io.ktor.http)
    implementation(libs.io.ktor.utils)

    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("runtimeClasspath")
}

