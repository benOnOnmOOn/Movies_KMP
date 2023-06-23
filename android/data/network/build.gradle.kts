plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    kotlin("kapt")
}

android {
    namespace = "com.bz.network"
}

dependencies {
    api(project(":data:dto"))

    ksp(libs.com.squareup.moshi.kotlin.codegen)

    // HILT
    implementation(libs.com.google.hilt.android)
    kapt(libs.com.google.hilt.android.compiler)
    implementation(libs.com.google.hilt.core)
    kapt(libs.com.google.dagger.compiler)
    api(libs.com.google.dagger)
    api(libs.javax.inject)
    //

    implementation(libs.com.squareup.moshi)
    implementation(libs.com.squareup.converter.moshi)
    api(libs.com.squareup.okhttp)
    api(libs.com.squareup.retrofit)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.ktx)

    // use debug impl to prevent from adding this deps to release version
    debugApi(libs.com.squareup.okhttp.logging.interceptor)

    testRuntimeOnly(libs.org.junit.jupiter.engine)

    testImplementation(libs.io.mockk.dsl)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.com.squareup.okhttp.mockwebserver)
    testImplementation(libs.org.junit.jupiter.api)

    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)

    runtimeOnly(libs.org.jetbrains.kotlinx.coroutines.android)
}
