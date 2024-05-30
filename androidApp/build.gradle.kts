import com.google.gms.googleservices.GoogleServicesTask

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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencyAnalysis {
    issues {
        onUnusedDependencies { exclude(":presentation:core") }
    }
}

dependencies {
    implementation(project(":presentation:core"))

    kover(project(":presentation:core"))
    kover(project(":presentation:screens"))
    kover(project(":data:database"))
    kover(project(":data:network"))
    kover(project(":data:dto"))

    implementation(libs.kotlin.stdlib)

    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.android)

    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.compose.runtime.android)

    implementation(libs.com.jakewharton.timber)

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

configurations {
    implementation {
        exclude("com.google.code.findbugs", "jsr305")
        exclude("com.google.errorprone", "error_prone_annotations")
        exclude("androidx.legacy", "legacy-support-core-utils")
        exclude("androidx.loader", "loader")
        exclude("androidx.privacysandbox.ads", "ads-adservices-java")
        exclude("androidx.privacysandbox.ads", "ads-adservices")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk7")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
        exclude("androidx.cursoradapter", "cursoradapter")
        exclude("androidx.customview", "customview")
        exclude("androidx.versionedparcelable", "versionedparcelable")
        exclude("androidx.vectordrawable", "vectordrawable-animated")
        exclude("androidx.vectordrawable", "vectordrawable")
        exclude("androidx.drawerlayout", "drawerlayout")
    }
}

dependencyGuard {
    // All dependencies included in Production Release APK
    configuration("releaseRuntimeClasspath")
}

project.afterEvaluate {
    tasks.withType<GoogleServicesTask> {
        gmpAppId.set(project.layout.buildDirectory.file("$name-gmpAppId.txt"))
    }
}
