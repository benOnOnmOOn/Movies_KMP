import com.bz.movies.findBooleanProperty
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BinaryCompatibilityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enablePlugin =
                findBooleanProperty("movies.enableApiDump", true)
            if (!enablePlugin) return

            pluginManager.apply("org.jetbrains.kotlinx.binary-compatibility-validator")

            extensions.configure<ApiValidationExtension> {
                ignoredPackages.add("hilt_aggregated_deps")
                nonPublicMarkers.addAll(
                    listOf(
                        "dagger.internal.DaggerGenerated",
                        "javax.annotation.processing.Generated",
                        "dagger.hilt.codegen.OriginatingElement",
                    ),
                )
            }
        }
    }
}
