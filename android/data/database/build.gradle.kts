plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    kotlin("kapt")
}

android {
    namespace = "com.bz.movies.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    api(project(":android:data:dto"))
    // HILT
    implementation(libs.com.google.hilt.android)
    kapt(libs.com.google.hilt.android.compiler)
    implementation(libs.com.google.hilt.core)
    kapt(libs.com.google.dagger.compiler)
    api(libs.com.google.dagger)
    api(libs.javax.inject)
    //

    implementation(libs.androidx.room.common)
    implementation(libs.androidx.sqlite)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)

    api(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testRuntimeOnly(libs.org.junit.jupiter.engine)

    testImplementation(libs.org.junit.jupiter.api)

    androidTestImplementation(libs.org.junit.jupiter.api)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(libs.org.junit.jupiter.api)
}
