import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            when {
                pluginManager.hasPlugin("com.android.application") ->
                    configure<ApplicationExtension> { lint.configure(project) }

                pluginManager.hasPlugin("com.android.library") ->
                    configure<LibraryExtension> { lint.configure(project) }

                else -> {
                    pluginManager.apply("com.android.lint")
                    configure<Lint> { configure(project) }
                }
            }
        }
    }
}

private fun Lint.configure(project: Project) {
    baseline = project.file("lint-baseline.xml")
    disable += listOf( "GradleDependency")
    abortOnError = true
    checkAllWarnings = true
    warningsAsErrors = true
    checkReleaseBuilds = false
    checkDependencies = false
    checkGeneratedSources = false
}
