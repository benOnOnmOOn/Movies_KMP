import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)

    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.android.library)
    alias(libs.plugins.movies.android.library.compose)
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}

kotlin {
    androidTarget {
        dependencies {
            implementation(project(":presentation:screens"))

            lintChecks(libs.slack.lint.checks)

            api(libs.androidx.activity)
            api(libs.kotlin.stdlib)

            implementation(libs.kermit)
            debugImplementation(libs.kermit.android.debug)
            debugImplementation(libs.kermit.core.android.debug)
            releaseImplementation(libs.kermit.core)

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
            export(project(":data:datastore"))
            export(project(":data:dto"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":presentation:screens"))
            api(project(":data:dto"))

            implementation(compose.components.resources)

            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(libs.coil)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.core)
            implementation(libs.coil.network.ktor)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viemodel)
            implementation(libs.koin.core)
            implementation(libs.koin.core.coroutines)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.material3)
            implementation(libs.navigation.compose)
        }

        androidMain.dependencies {
            implementation(project(":presentation:screens"))

            api(libs.androidx.activity)
            api(libs.androidx.compose.runtime)
            api(libs.kotlin.stdlib)

            implementation(libs.kermit)

            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.foundation.layout)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.navigation.common)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.navigation.runtime)

            implementation(libs.kotlinx.coroutines.android)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

afterEvaluate {
    tasks.named("explodeCodeSourceDebug").dependsOn("generateActualResourceCollectorsForAndroidMain")
    tasks.named("explodeCodeSourceRelease").dependsOn("generateActualResourceCollectorsForAndroidMain")
}
