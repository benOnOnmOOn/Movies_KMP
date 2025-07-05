import com.android.build.api.dsl.ApplicationExtension
import com.bz.movies.findBooleanProperty
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val enablePlugin =
                findBooleanProperty("movies.enableFirebase", default = true)
            if (!enablePlugin) return

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    freeCompilerArgs.addAll(listOf("-Xexpect-actual-classes"))
                    allWarningsAsErrors.set(false)
                    extraWarnings.set(true)
                }
            }

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
