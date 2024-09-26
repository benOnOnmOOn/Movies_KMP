plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.com.google.gms.google.services) apply false
    alias(libs.plugins.com.google.firebase.crashlytics.gradle) apply false
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.guard)
    alias(libs.plugins.dexcount)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }

    reports {
        variant("custom") {
            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }
        filters {
            excludes {
                annotatedBy(
                    "*Generated*",
                    "*Composable*",
                )
            }
        }
    }
}

android {
    namespace = "com.bz.movies.kmp.android"

    defaultConfig {
        applicationId = "com.bz.movies.kmp"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            apply(plugin = "com.google.gms.google-services")
            apply(plugin = "com.google.firebase.crashlytics")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":presentation:core"))
    implementation(project(":presentation:screens"))

    kover(project(":presentation:core"))
    kover(project(":presentation:screens"))
    kover(project(":data:database"))
    kover(project(":data:network"))
    kover(project(":data:dto"))

    implementation(libs.kotlin.stdlib)

    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.android)

    implementation(libs.androidx.startup.runtime)

    implementation(libs.kermit)
    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)
    releaseImplementation(libs.kermit.crashlytics)
    releaseImplementation(libs.kermit.core)

    lintChecks(libs.slack.lint.checks)

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    compileOnly(libs.google.services)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("releaseRuntimeClasspath")
}
