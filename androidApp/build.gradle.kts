plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.com.google.gms.google.services) apply false
    alias(libs.plugins.com.google.firebase.crashlytics.gradle) apply false
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    kotlin("android")
    kotlin("kapt")
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
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
}


dependencyAnalysis {
    issues { onUnusedDependencies { exclude(":presentation:core") } }
}

dependencies {
    implementation(project(":shared"))
    api(project(":android:presentation:core"))

    kover(project(":shared"))
    kover(project(":android:presentation:core"))
    kover(project(":android:presentation:screens"))
    kover(project(":android:data:network"))
    kover(project(":android:data:database"))
    kover(project(":android:data:dto"))

    implementation(libs.androidx.compose.ui)
    runtimeOnly(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.unit)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)

    releaseImplementation(platform(libs.com.google.firebase.bom))

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    //  HILT
    kapt(libs.com.google.hilt.android.compiler)
    implementation(libs.com.google.hilt.android)
    implementation(libs.com.google.hilt.core)
    //

    implementation(libs.androidx.startup.runtime)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)
}
