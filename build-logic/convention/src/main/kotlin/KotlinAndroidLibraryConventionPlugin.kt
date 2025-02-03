import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.bz.movies.configureKotlinAndroid
import com.bz.movies.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.androidLibrary
import com.android.builder.model.AndroidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import kotlin.plus

@Suppress("UnstableApiUsage", "Deprecated")
class KotlinAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.kotlin.multiplatform.library")

            extensions.configure < KotlinMultiplatformExtension> {
                androidLibrary {

                    namespace = "com.bz.movies" + target.project.path.replace(':', '.')
                    compileSdk = 35
                    buildToolsVersion = "35.0.0"
                    minSdk = 27

                    lint {
                        baseline = project.file("lint-baseline.xml")
                        disable +=
                            listOf(
                                "NewerVersionAvailable",
                                "GradleDependency",
                                "ObsoleteLintCustomCheck",
                            )
                        abortOnError = true
                        checkAllWarnings = true
                        warningsAsErrors = true
                        checkReleaseBuilds = false
                        checkDependencies = false
                        checkGeneratedSources = false
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
            }
        }
    }
}