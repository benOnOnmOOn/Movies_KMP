import com.bz.movies.kmp.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

@Suppress("FunctionName")
fun InitKoin() {
    startKoin {
        lazyModules(presentationModule)
    }
}
