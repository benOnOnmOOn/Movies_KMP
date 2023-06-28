plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    kotlin("kapt")
}

android {
    namespace = "com.bz.presentation.screens"

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
        )
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":data:network"))
    api(project(":android:data:database"))
    api(project(":data:dto"))

    releaseImplementation(platform(libs.com.google.firebase.bom))

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    //  HILT
    kapt(libs.com.google.hilt.android.compiler)
    kapt(libs.com.google.dagger.compiler)
    implementation(libs.com.google.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.com.google.hilt.core)
    api(libs.com.google.dagger)
    api(libs.javax.inject)
    //

    implementation(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.common)
    api(libs.androidx.navigation.runtime)

    api(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.unit)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.compose.material3)
    runtimeOnly(libs.androidx.startup.runtime)
    implementation(libs.io.coil.compose)

    implementation(libs.org.jetbrains.kotlinx.coroutines.core)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.io.mockk)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testRuntimeOnly(libs.org.junit.jupiter.engine)

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
    debugRuntimeOnly(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.monitor)
    androidTestRuntimeOnly(libs.org.junit.jupiter.engine)
    androidTestImplementation(libs.org.junit.jupiter.api)


}
