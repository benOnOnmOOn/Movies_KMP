package com.bz.movies

import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.ProductFlavor
import com.android.build.api.dsl.BuildType
import kotlin.collections.plusAssign
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun ApplicationExtension.baseConfig(project: Project) {
    defaultBaseConfig(project)
    dependenciesInfo.apply {
        includeInApk = false
        includeInBundle = false
    }

    @Suppress("UnstableApiUsage", "MissingResourcesProperties")
    androidResources.generateLocaleConfig = true

    defaultConfig {
        applicationId = "com.bz.movies"
        versionCode = 1
        versionName = "1.0"

        targetSdk = 35
        multiDexEnabled = false
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

//            optimization {
//                keepRules {
//                    ignoreAllExternalDependencies(true)
//                }
//            }
            proguardFiles("proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions.isCoreLibraryDesugaringEnabled = false
}


//region Global android configuration
fun <
        BF : BuildFeatures,
        BT : BuildType,
        DC : DefaultConfig,
        PF : ProductFlavor,
        AR : AndroidResources,
        IN : Installation,
        > CommonExtension<BF, BT, DC, PF, AR, IN>.defaultBaseConfig(project: Project) {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        minSdk = 27
        resourceConfigurations += listOf("pl", "en")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    packaging.resources.excludes +=
        setOf(
            "kotlin/**",
            "META-INF/**",
            "META-INF/services/**",
            "**.properties",
            "kotlin-tooling-metadata.json",
            "DebugProbesKt.bin"
        )
}

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroidApp(commonExtension: ApplicationExtension) {
    commonExtension.baseConfig(this)
    configureKotlin<KotlinBaseExtension>()
}

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.defaultBaseConfig(this)
    configureKotlin<KotlinBaseExtension>()
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    configureKotlin<KotlinBaseExtension>()
}

/**
 * Configure base Kotlin options
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {

}
