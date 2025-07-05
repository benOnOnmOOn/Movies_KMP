import com.android.build.api.dsl.ApplicationExtension
import com.bz.movies.findBooleanProperty
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enablePlugin =
                findBooleanProperty("movies.enableFirebase", default = true)
            if (!enablePlugin) return

            extensions.configure<ApplicationExtension> {
                buildTypes {
                    release {
                        with(pluginManager) {
                            apply("com.google.gms.google-services")
                            apply("com.google.firebase.firebase-perf")
                            apply("com.google.firebase.crashlytics")
                        }
                        configure<CrashlyticsExtension> {
                            mappingFileUploadEnabled = false
                        }
                    }
                }
            }
        }
    }
}
