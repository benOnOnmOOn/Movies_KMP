import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlinx.kover")

            when {
                pluginManager.hasPlugin("com.android.application") ->
                    configure<KoverProjectExtension> { configure() }

                pluginManager.hasPlugin("com.android.library") ->
                    configure<KoverProjectExtension> { configure() }
            }
        }
    }
}

private fun KoverProjectExtension.configure() {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }

    reports {
        total {
            html {
                onCheck.set(true)
            }
            xml {
                onCheck.set(true)
            }
        }
        filters {
            excludes {
                classes(
                    // moshi json adapter
                    "com.bz.network.api.model.*JsonAdapter",
                    "*ComposableSingletons*",
                    "*_Factor*y",
                    "*_HiltModules*",
                    "*Hilt_*",
                    "*_Impl*",
                    "com.bz.movies.core.CrashlyticsLogTree"
                )
                packages(
                    "hilt_aggregated_deps",
                    "dagger.hilt.internal.aggregatedroot.codegen",
                    "com.bz.movies.database.dao",
                    "com.bz.movies.presentation.theme",
                    "com.bz.movies.presentation.navigation",
                    "com.bz.movies.presentation.screens.utils",
                    "com.bz.movies.core"
                )
                annotatedBy(
                    "*Generated*",
                    "*Composable*",
                    "*Module*",
                    "*HiltAndroidApp*",
                    "*AndroidEntryPoint*"
                )
            }
        }
    }
}
