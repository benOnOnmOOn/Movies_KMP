import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
    alias(libs.plugins.dependency.analysis) apply true
    alias(libs.plugins.ktlint) apply true
}

group = "com.bz.movies.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        allWarningsAsErrors.set(false)
        extraWarnings.set(true)
        progressiveMode = true
    }
}

ktlint {
    version.set("1.6.0")
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.binary.compatibility.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.dependency.analysis.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.multiplatform.android.library.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    lintChecks(libs.androidx.lint.gradle)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id =
                libs.plugins.movies.android.application
                    .asProvider()
                    .get()
                    .pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidRoom") {
            id =
                libs.plugins.movies.android.room
                    .get()
                    .pluginId
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFirebase") {
            id =
                libs.plugins.movies.android.application.firebase
                    .get()
                    .pluginId
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidLint") {
            id =
                libs.plugins.movies.android.lint
                    .get()
                    .pluginId
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("kover") {
            id =
                libs.plugins.movies.kover
                    .get()
                    .pluginId
            implementationClass = "KoverConventionPlugin"
        }
        register("binaryCompatibility") {
            id =
                libs.plugins.movies.binary.compatibility
                    .get()
                    .pluginId
            implementationClass = "BinaryCompatibilityConventionPlugin"
        }
        register("strictDependenciesConventionPlugin") {
            id =
                libs.plugins.movies.strict.dependencies
                    .get()
                    .pluginId
            implementationClass = "StrictDependenciesConventionPlugin"
        }
        register("dependencyAnalysisConventionPlugin") {
            id =
                libs.plugins.movies.dependency.analysis
                    .get()
                    .pluginId
            implementationClass = "DependencyAnalysisConventionPlugin"
        }
        register("ktlintConventionPlugin") {
            id =
                libs.plugins.movies.ktlint
                    .get()
                    .pluginId
            implementationClass = "KtlintConventionPlugin"
        }
        register("KmpLibraryConventionPlugin") {
            id =
                libs.plugins.movies.kmp.library
                    .get()
                    .pluginId
            implementationClass = "KmpLibraryConventionPlugin"
        }
    }
}
