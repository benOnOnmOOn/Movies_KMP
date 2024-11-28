import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.android.AndroidCacheFixPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.com.github.ben.manes.versions) apply true
    alias(libs.plugins.io.gitlab.arturbosch.detekt) apply true
    alias(libs.plugins.com.autonomousapps.dependency.analysis) apply true
    alias(libs.plugins.com.google.gms.google.services) apply false
    alias(libs.plugins.org.jetbrains.kotlinx.kover) apply true
    alias(libs.plugins.com.osacky.doctor) apply true
    alias(libs.plugins.app.cash.sqldelight) apply false
    alias(libs.plugins.org.jlleitschuh.gradle.ktlint) apply true
    alias(libs.plugins.org.gradle.android.cache.fix) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.binary.compatibility)
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

//region Dependency Updates Task

fun isNonStable(version: String): Boolean {
    val unStableKeyword =
        listOf("ALPHA", "BETA", "RC").any {
            version.contains(it, ignoreCase = true)
        }
    if (unStableKeyword) return true

    val stableKeyword =
        listOf("RELEASE", "FINAL", "GA").any {
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
}

tasks.register<Detekt>("detektAll") {
    description = "Runs Detekt for all modules"
    group = "verification"
    jvmTarget = "21"
    config.setFrom(files(configFile))
    baseline = file(baselineFile)
    setSource(projectSource)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.register<DetektCreateBaselineTask>("detektGenerateBaseline") {
    description = "Custom DETEKT build to build baseline for all modules"
    group = "verification"
    setSource(projectSource)
    config.setFrom(files(configFile))
    baseline = file(baselineFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    jvmTarget = "21"
}

//endregion

//region Global kotlin configuration
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)

        freeCompilerArgs.addAll(listOf("-Xjvm-default=all", "-Xexpect-actual-classes"))
        allWarningsAsErrors.set(true)
        extraWarnings.set(true)
    }
}

//endregion

dependencyAnalysis {
    issues {
        all { onAny { severity("fail") } }
    }
}

fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions.getByType<BaseAppModuleExtension>().baseConfig(project)
            }

            is LibraryPlugin -> {
                project.extensions.getByType<LibraryExtension>().baseConfig(project)
            }
        }
    }
}

//region Global android configuration
fun <
    BF : BuildFeatures,
    BT : BuildType,
    DC : DefaultConfig,
    PF : ProductFlavor,
    AR : AndroidResources,
    IN : Installation,
> CommonExtension<BF, BT, DC, PF, AR, IN>.defaultBaseConfig(
    project: Project,
) {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations.addAll(listOf("en", "pl"))
    }

    lint {
        baseline = project.file("lint-baseline.xml")
        disable += listOf("NewerVersionAvailable", "GradleDependency", "ObsoleteLintCustomCheck")
        abortOnError = true
        checkAllWarnings = true
        warningsAsErrors = true
        checkReleaseBuilds = false
        checkDependencies = false
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
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    packaging.resources.excludes +=
        setOf(
            "kotlin/**",
            "META-INF/**",
            "**.properties",
            "kotlin-tooling-metadata.json",
            "DebugProbesKt.bin",
        )
}

fun LibraryExtension.baseConfig(project: Project) {
    defaultBaseConfig(project)
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

fun BaseAppModuleExtension.baseConfig(project: Project) {
    defaultBaseConfig(project)
    dependenciesInfo.apply {
        includeInApk = false
        includeInBundle = false
    }
}

subprojects {
    apply<KtlintPlugin>()
    apply<AndroidCacheFixPlugin>()
    project.plugins.applyBaseConfig(project)
    configurations.all {
        exclude("androidx.legacy", "legacy-support-core-utils")
        exclude("androidx.loader", "loader")
        exclude("androidx.privacysandbox.ads", "ads-adservices-java")
        exclude("androidx.privacysandbox.ads", "ads-adservices")
        exclude("androidx.cursoradapter", "cursoradapter")
        exclude("androidx.customview", "customview")
        exclude("androidx.versionedparcelable", "versionedparcelable")
        exclude("androidx.vectordrawable", "vectordrawable-animated")
        exclude("androidx.vectordrawable", "vectordrawable")
        exclude("androidx.drawerlayout", "drawerlayout")
        exclude("androidx.fragment", "fragment")
        exclude("androidx.fragment", "fragment-ktx")
//        exclude("androidx.activity", "activity-ktx") we need to add this to have working compose preview
        exclude("androidx.collection", "collection-ktx")
        exclude("androidx.savedstate","savedstate-ktx")
        exclude("androidx.lifecycle", "lifecycle-runtime-ktx-android")
        exclude("androidx.lifecycle", "lifecycle-runtime-ktx")
        exclude("androidx.lifecycle", "viewmodel-ktx")
        exclude("androidx.lifecycle", "lifecycle-viewmodel-ktx")
        exclude("androidx.navigation", "navigation-common-ktx")
        exclude("androidx.navigation", "navigation-runtime-ktx")
        exclude("androidx.appcompat", "appcompat")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk7")
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
        exclude("com.google.code.findbugs", "jsr305")
        exclude("com.google.errorprone", "error_prone_annotations")
        exclude("org.checkerframework", "checker-qual")
    }
}
// endregion

ktlint {
    version.set("1.4.1")
}

doctor {
    daggerThreshold.set(100)
    negativeAvoidanceThreshold.set(50)
}
