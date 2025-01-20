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
        variant("custom") {
            xml {
                onCheck.set(true)
            }
            html {
                onCheck.set(true)
            }
        }
        filters {
            excludes {
                annotatedBy("*Generated*", "*Composable*")
            }
        }
    }
}
