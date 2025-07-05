import com.android.build.api.dsl.ApplicationExtension
import com.bz.movies.configureKotlinAndroidApp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin = "com.android.application")
                apply(plugin = "org.jetbrains.kotlin.android")
                apply(plugin = "movies.android.lint")
                apply(plugin = "com.dropbox.dependency-guard")
                apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    freeCompilerArgs.addAll(listOf("-Xexpect-actual-classes"))
                    allWarningsAsErrors.set(false)
                    extraWarnings.set(true)
                }
            }

            extensions.configure<ApplicationExtension> {
                target.configureKotlinAndroidApp(this)
            }
        }
    }
}
