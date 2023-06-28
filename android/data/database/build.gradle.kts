plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
}

android {
    namespace = "com.bz.movies.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    api(project(":data:dto"))
    implementation(libs.io.insert.koin.core)
    implementation(libs.io.insert.koin.android)

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
