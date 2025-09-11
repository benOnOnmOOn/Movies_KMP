plugins {
    alias(libs.plugins.dexcount)

    alias(libs.plugins.movies.android.application)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

android {
    namespace = "com.bz.movies.kmp.android"
    buildTypes {
        release {
            optimization {
                keepRules {
                    ignoreFrom(
                        "androidx.compose.animation:animation-android",
                        "androidx.compose.animation:animation-core-android",
                        "androidx.compose.animation:animation-core",
                        "androidx.compose.animation:animation",
                        "androidx.compose.foundation:foundation-android",
                        "androidx.compose.foundation:foundation-layout-android",
                        "androidx.compose.foundation:foundation-layout",
                        "androidx.compose.foundation:foundation",
                        "androidx.compose.material3:material3-android",
                        "androidx.compose.material3:material3",
                        "androidx.compose.material:material-ripple",
                        "androidx.compose.material:material",
                        "androidx.compose.runtime:runtime-android",
                        "androidx.compose.runtime:runtime-annotation-android",
                        "androidx.compose.runtime:runtime-annotation",
                        "androidx.compose.runtime:runtime-saveable-android",
                        "androidx.compose.runtime:runtime-saveable",
                        "androidx.compose.runtime:runtime",
                        "androidx.compose.ui:ui-android",
                        "androidx.compose.ui:ui-geometry-android",
                        "androidx.compose.ui:ui-geometry",
                        "androidx.compose.ui:ui-graphics-android",
                        "androidx.compose.ui:ui-graphics",
                        "androidx.compose.ui:ui-text-android",
                        "androidx.compose.ui:ui-text",
                        "androidx.compose.ui:ui-unit-android",
                        "androidx.compose.ui:ui-unit",
                        "androidx.compose.ui:ui-util-android",
                        "androidx.compose.ui:ui-util",
                        "androidx.compose.ui:ui"
                    )
                }
            }
            proguardFiles("proguard-rules-compose.pro")
        }
    }
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
