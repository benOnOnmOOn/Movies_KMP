plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}


android {
    namespace = "com.bz.presentation.screens"

    kotlinOptions {
        freeCompilerArgs +=
            listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            )
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":data:network"))
    api(project(":data:database"))
    api(project(":data:dto"))

    releaseImplementation(libs.com.google.firebase.analytics.ktx)
    releaseImplementation(libs.com.google.firebase.crashlytics.ktx)

    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.compose)
    implementation(libs.io.insert.koin.android)
    implementation(libs.io.insert.koin.android.compose)

    api(libs.androidx.navigation.common)
    api(libs.androidx.navigation.runtime)
    api(libs.androidx.compose.ui.android)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime.android)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.compose.material3)
    api(libs.androidx.activity.compose.animation.android)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.graphics.android)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation.layout.android)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.text.android)
    implementation(libs.androidx.compose.ui.unit.android)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    runtimeOnly(libs.androidx.startup.runtime)

    implementation(libs.io.coil.compose)
    implementation(libs.io.coil.base)
    implementation(libs.coil.compose.base)

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
