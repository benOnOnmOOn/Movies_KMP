plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

group = "com.bz.movies"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.io.ktor.http)

    runtimeOnly(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("runtimeClasspath")
}
