plugins {
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.movies.kmp.library)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            api(projects.data.dto)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.koin.core.coroutines)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.core)
            implementation(libs.androidx.core.ktx)

            implementation(libs.koin.android)

            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.http)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.utils)

            implementation(libs.slf4j.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}
