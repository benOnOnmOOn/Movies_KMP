import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

        iosX64()
        iosArm64()
        iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                // put your multiplatform dependencies here
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
