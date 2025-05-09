import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UnstableApiUsage")
class KotlinAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.kotlin.multiplatform.library")

            extensions.configure<KotlinMultiplatformExtension> {
                androidLibrary {
                    namespace = "com.bz.movies" + target.project.path.replace(':', '.')
                    compileSdk = 36
                    buildToolsVersion = "36.0.0"
                    minSdk = 27

                    lint {
                        baseline = project.file("lint-baseline.xml")
                        disable += listOf("GradleDependency",)
                        abortOnError = true
                        checkAllWarnings = true
                        warningsAsErrors = true
                        checkReleaseBuilds = false
                        checkDependencies = false
                        checkGeneratedSources = false
                    }

                    compilations.configureEach {
                        compilerOptions.configure { jvmTarget.set(JvmTarget.JVM_21) }
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
