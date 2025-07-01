plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {

    jvmToolchain(21)

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()
}
