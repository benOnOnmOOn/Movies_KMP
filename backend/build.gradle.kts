import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)

//    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.strict.dependencies)
//    alias(libs.plugins.ktor)
}


kotlin {
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

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
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.http)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.content.negotiation)
                implementation(libs.ktor.utils)
            }
        }
    }
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("nativeCInterop")
}
