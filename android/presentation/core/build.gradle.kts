plugins {
    embeddedKotlin("android")
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
}

android {
    namespace = "com.bz.core"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":android:presentation:screens"))
    implementation(project(":data:network"))
    implementation(project(":data:database"))

    releaseImplementation(platform(libs.com.google.firebase.bom))

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.android)

    api(libs.androidx.activity)
    api(libs.androidx.compose.runtime.android)
    implementation(libs.androidx.compose.foundation.layout.android)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.android)
    implementation(libs.androidx.compose.ui.graphics.android)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.navigation.runtime)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)
}
