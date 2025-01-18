import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.bz.movies.configureKotlinAndroid
import com.bz.movies.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
//            if(!pluginManager.hasPlugin("org.jetbrains.kotlin.android")) {
//                pluginManager.apply("org.jetbrains.kotlin.android")
//            }
            pluginManager.apply("movies.android.lint")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                namespace = "com.bz.movies" + target.project.path.replace(':', '.')
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
        }
    }
}
