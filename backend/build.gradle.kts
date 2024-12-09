plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val nativeTarget =
        when {
            hostOs == "Mac OS X" && arch == "x86_64" -> macosX64("native")
            hostOs == "Mac OS X" && arch == "aarch64" -> macosArm64("native")
            hostOs == "Linux" -> linuxX64("native")
            // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.example.main"
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.ktor.server.cio)
//                implementation("io.ktor:ktor-server-cio:$ktor_version")
                implementation(libs.io.ktor.http)
                implementation(libs.ktor.server.content.negotiation)
                implementation(libs.io.ktor.serialization.kotlinx.json)
                implementation(libs.io.ktor.serialization)
                implementation(libs.io.ktor.utils)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}

dependencyGuard {

    // All dependencies included in Production Release APK
    configuration("nativeCInterop") {
        tree = true
    }
}
