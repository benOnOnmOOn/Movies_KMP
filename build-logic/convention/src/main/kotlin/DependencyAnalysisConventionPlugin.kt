import com.autonomousapps.DependencyAnalysisExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class DependencyAnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enablePlugin =
                providers.gradleProperty("movies.enableDependencyAnalysis").getOrElse("true").toBoolean()
            if (!enablePlugin) return

            pluginManager.apply("com.autonomousapps.dependency-analysis")
            extensions.findByType<DependencyAnalysisExtension>()?.issues {
                all { onAny { severity("fail") } }
            }
        }
    }
}
