import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}

kotlin {
    androidTarget {
        dependencies {
            implementation(project(":data:database"))
            implementation(project(":data:network"))
            implementation(project(":presentation:screens"))

            lintChecks(libs.slack.lint.checks)

            api(libs.androidx.activity)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.navigation.common)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.navigation.runtime)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../../iosApp/Podfile")
        name = "presentationCore"
        framework {
            linkerOpts.add("-lsqlite3")
            baseName = "presentationCore"
            isStatic = true
            export(project(":presentation:screens"))
            export(project(":data:network"))
            export(project(":data:database"))
            export(project(":data:dto"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":presentation:screens"))
            api(project(":data:network"))
            api(project(":data:database"))
            api(project(":data:dto"))

            implementation(compose.components.resources)
            implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(libs.material3)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.compose.core)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.io.insert.koin.core)
            implementation(libs.io.insert.koin.compose)
            implementation(libs.io.insert.koin.compose.viemodel)
        }

        androidMain.dependencies {
            implementation(project(":data:database"))
            implementation(project(":data:network"))
            implementation(project(":presentation:screens"))

            api(libs.androidx.activity)
            api(libs.androidx.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.foundation)
            implementation(libs.androidx.foundation.layout)
            implementation(libs.androidx.navigation.common)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.navigation.runtime)
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.graphics)

            implementation(libs.org.jetbrains.kotlinx.coroutines.android)
            implementation(libs.androidx.compose.ui.tooling.preview)
        }

        iosMain.dependencies {
            implementation(libs.app.cash.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "com.bz.core"

    buildFeatures {
        compose = true
    }
}

afterEvaluate {
    tasks.named("explodeCodeSourceDebug").dependsOn("generateActualResourceCollectorsForAndroidMain")
    tasks.named("explodeCodeSourceRelease").dependsOn("generateActualResourceCollectorsForAndroidMain")
}
