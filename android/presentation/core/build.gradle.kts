plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    kotlin("kapt")
}

android {
    namespace = "com.bz.core"

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":android:presentation:screens"))

    releaseImplementation(platform(libs.com.google.firebase.bom))

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    //  HILT
    kapt(libs.com.google.hilt.android.compiler)
    api(libs.com.google.hilt.android)
    api(libs.com.google.hilt.core)
    //

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.lifecycle.common)
    api(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.navigation.runtime)

    api(libs.androidx.activity)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)

}
