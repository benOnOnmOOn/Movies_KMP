import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import kotlinx.kover.gradle.plugin.KoverGradlePlugin
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.com.github.ben.manes.versions) apply true
    alias(libs.plugins.io.gitlab.arturbosch.detekt) apply true
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
    alias(libs.plugins.com.google.gms.google.services) apply false
    alias(libs.plugins.org.jetbrains.kotlinx.kover) apply false
    alias(libs.plugins.com.osacky.doctor) apply true
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//region Dependency Updates Task

fun isNonStable(version: String): Boolean {
    val unStableKeyword = listOf("ALPHA", "BETA").any {
        version.contains(it, ignoreCase = true)
    }
    if (unStableKeyword) return true

    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.contains(it, ignoreCase = true)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

//endregion

//region Detekt

val projectSource = file(projectDir)
val configFile = files("$rootDir/config/detekt/detekt.yml")
val baselineFile = file("$rootDir/config/detekt/baseline.xml")
val kotlinFiles = "**/*.kt"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files(configFile))
    baseline = file(baselineFile)
    parallel = true
    ignoreFailures = false
    autoCorrect = false
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}
tasks.register<Detekt>("detektAll") {
    description = "Runs Detekt for all modules"
    jvmTarget = "17"
    allRules = false
    config = files(configFile)
    baseline = file(baselineFile)
    setSource(projectSource)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
}

tasks.register<DetektCreateBaselineTask>("detektGenerateBaseline") {
    description = "Custom DETEKT build to build baseline for all modules"
    setSource(projectSource)
    baseline.set(baselineFile)
    config.setFrom(configFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}

//endregion

//region Global kotlin configuration
allprojects {
    tasks.withType<KotlinCompile>().configureEach {
      compilerOptions{
          jvmTarget.set(JvmTarget.JVM_17)

          freeCompilerArgs.add("-Xjvm-default=all")
      }
    }
}
//endregion

dependencyAnalysis {
    issues { all { onAny { severity("fail") } } }
}


fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions.getByType<BaseAppModuleExtension>().baseConfig()
            }

            is LibraryPlugin -> {
                project.extensions.getByType<LibraryExtension>().baseConfig()
            }

            is Kapt3GradleSubplugin -> {
                project.extensions.getByType<KaptExtension>().baseConfig()
            }

            is KoverGradlePlugin -> {
                project.extensions.getByType<KoverReportExtension>().baseConfig()
            }
        }
    }
}

fun KaptExtension.baseConfig() {
    correctErrorTypes = true
    useBuildCache = true
}

//region Global android configuration
fun <BF : BuildFeatures, BT : BuildType, DC : DefaultConfig, PF : ProductFlavor>
        CommonExtension<BF, BT, DC, PF>.defaultBaseConfig() {
    compileSdk = libs.versions.android.sdk.target.get().toInt()
//    buildToolsVersion = "34.0.0"

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations.addAll(listOf("en", "pl"))
    }

    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = true
        checkAllWarnings = true
        warningsAsErrors = true
        checkReleaseBuilds = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                @Suppress("UnstableApiUsage")
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compose.compiler.extension.get()
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

}

fun LibraryExtension.baseConfig() {
    defaultBaseConfig()
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

}

fun BaseAppModuleExtension.baseConfig() {
    defaultBaseConfig()
    defaultConfig {
        targetSdk = libs.versions.android.sdk.target.get().toInt()
    }

}

subprojects {
    project.plugins.applyBaseConfig(project)
}
// endregion

fun KoverReportExtension.baseConfig() {
    androidReports("debug") {
        html {
            onCheck = true
        }
        xml {
            onCheck = true
        }
    }
}
