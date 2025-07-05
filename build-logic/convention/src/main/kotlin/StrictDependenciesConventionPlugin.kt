import com.bz.movies.getBooleanProperty
import com.bz.movies.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude

class StrictDependenciesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enablePlugin =
                getBooleanProperty("movies.enableStrictDependency", true)
            if (!enablePlugin) return

            target.configurations.configureEach {
                exclude("androidx.appcompat", "appcompat")
                exclude("androidx.collection", "collection-ktx")
                exclude("androidx.cursoradapter", "cursoradapter")
                exclude("androidx.customview", "customview")
                exclude("androidx.drawerlayout", "drawerlayout")
                exclude("androidx.fragment", "fragment-ktx")
                exclude("androidx.legacy", "legacy-support-core-utils")
                exclude("androidx.lifecycle", "lifecycle-common-java8")
                exclude("androidx.lifecycle", "lifecycle-runtime-ktx")
                exclude("androidx.lifecycle", "lifecycle-runtime-ktx-android")
                exclude("androidx.lifecycle", "lifecycle-viewmodel-ktx")
                exclude("androidx.lifecycle", "viewmodel-ktx")
                exclude("androidx.loader", "loader")
                exclude("androidx.navigation", "navigation-common-ktx")
                exclude("androidx.navigation", "navigation-runtime-ktx")
                exclude("androidx.privacysandbox.ads", "ads-adservices")
                exclude("androidx.privacysandbox.ads", "ads-adservices-java")
                exclude("androidx.savedstate", "savedstate-ktx")
                exclude("androidx.vectordrawable", "vectordrawable")
                exclude("androidx.vectordrawable", "vectordrawable-animated")
                exclude("androidx.versionedparcelable", "versionedparcelable")

                exclude("com.google.code.findbugs", "jsr305")
                exclude("com.google.errorprone", "error_prone_annotations")
                exclude("org.checkerframework", "checker-qual")

                exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk7")
                exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
                exclude("org.jetbrains.kotlin", "kotlin-reflect")

                // force deps
                resolutionStrategy {
                    libs.libraryAliases.forEach {
                        val lib =
                            libs
                                .findLibrary(it)
                                .get()
                                .get()
                                .module
                        val version =
                            libs
                                .findLibrary(it)
                                .get()
                                .get()
                                .versionConstraint
                        logger.info("Forcing library $lib with version:$version")
                        force("$lib:$version")
                    }
                }
            }
        }
    }
}
