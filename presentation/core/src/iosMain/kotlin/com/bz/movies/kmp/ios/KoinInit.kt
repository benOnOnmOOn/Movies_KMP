import com.bz.movies.kmp.di.presentationModule
import org.koin.core.context.startKoin

@Suppress("FunctionName")
fun InitKoin() {
    startKoin {
        modules(presentationModule)
    }
}
